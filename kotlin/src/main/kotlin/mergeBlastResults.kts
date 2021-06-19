import java.io.File

data class BLASTResult(
    val qseqid: String,
    val sseqid: String,
    val pident: Double,
    val length: Int,
    val mismatch: Int,
    val gapopen: Int,
    val qstart: Int,
    val qend: Int,
    val sstart: Int,
    val send: Int,
    val evalue: Double,
    val bitscore: Double,
    val qlen: Int,
    val slen: Int,
    val nident: Int,
    val positive: Int
) {
    val sstrand = sstart < send
    val queryIdentity: Double = nident / qlen.toDouble()
    val subjectIdentity: Double = nident / slen.toDouble()
    val queryPositive: Double = positive / qlen.toDouble()
    val subjectPositive: Double = positive / slen.toDouble()

    override fun toString(): String {
        return listOf(
            qseqid, sseqid, pident, length, mismatch, gapopen,
            qstart, qend, sstart, send, evalue, bitscore, qlen,
            slen, nident, positive
        ).joinToString("\t")
    }

    companion object {
        fun header(): String = listOf(
            "# qseqid", "sseqid", "pident", "length", "mismatch", "gapopen", "qstart", "qend", "sstart", "send",
            "evalue", "bitscore", "qlen", "slen", "nident", "positive"
        ).joinToString("\t")
    }

}

fun readBlastResults(file: File): List<BLASTResult> {
    val reader = file.bufferedReader()
    val results = mutableListOf<BLASTResult>()
    reader.forEachLine {
        val line = it.split("\t")
        results.add(
            BLASTResult(
                qseqid = line[0],
                sseqid = line[1],
                pident = line[2].toDouble(),
                length = line[3].toInt(),
                mismatch = line[4].toInt(),
                gapopen = line[5].toInt(),
                qstart = line[6].toInt(),
                qend = line[7].toInt(),
                sstart = line[8].toInt(),
                send = line[9].toInt(),
                evalue = line[10].toDouble(),
                bitscore = line[11].toDouble(),
                qlen = line[12].toInt(),
                slen = line[13].toInt(),
                nident = line[14].toInt(),
                positive = line[15].toInt()
            )
        )
    }
    return results
}

data class MutableInterval(val name: String, val chrom: String, var targetStart: Int, var targetEnd: Int, var targetStrand: Boolean,
                           var queryStart: Int, var queryEnd: Int, var numPositive: Int)

fun mergeBlastResults(blastResults: List<BLASTResult>,
                      maxTargetGap: Int,
                      maxTargetOverlap: Int,
                      maxQueryGap: Int,
                      maxQueryOverlap: Int,
                      maxEvalue: Double
): List<MutableInterval> {
    val sortedResults = blastResults
        .filter { it.evalue < maxEvalue }
        .map { with(it) {
            MutableInterval(qseqid, sseqid, minOf(sstart, send), maxOf(sstart, send), sstrand, qstart, qend, positive) }
        }
//        .sortedWith(compareBy({it.targetStrand}, {it.targetStart}, {it.targetEnd}))
        .sortedBy { it.targetStart }
        .toMutableList()

    val finalResults = mutableListOf<MutableInterval>()
    val tempResults = mutableListOf(sortedResults.first())

    fun nextPassesTargetFilter(a: MutableInterval, b: MutableInterval, maxTargetGap: Int, maxTargetOverlap: Int) =
        when {
            b.targetStart - a.targetEnd > maxTargetGap -> false
            a.targetEnd - b.targetStart > maxTargetOverlap -> false
            else -> true
        }

    fun processCluster(intervals: List<MutableInterval>, maxQueryGap: Int, maxQueryOverlap: Int): List<MutableInterval> {
        val sortedIntervals = intervals.sortedBy { it.queryStart }
        val clusterOutput = mutableListOf<MutableInterval>()
        val clusterTemp = mutableListOf(sortedIntervals.first())

        fun nextPassesQueryFilter(a: MutableInterval, b: MutableInterval, maxQueryGap: Int, maxQueryOverlap: Int) =
            when {
                b.queryStart - a.queryStart > maxQueryGap -> false
                a.queryEnd - b.queryStart > maxQueryOverlap -> false
                else -> false
            }

        fun mergeCluster(mutableIntervals: List<MutableInterval>): MutableInterval =
            MutableInterval(
                name = mutableIntervals.first().name,
                chrom = mutableIntervals.first().chrom,
//                targetStart = mutableIntervals.minOf { it.targetStart },
                targetStart = mutableIntervals.map { it.targetStart }.min()!!,
//                targetEnd = mutableIntervals.maxOf { it.targetEnd },
                targetEnd = mutableIntervals.map { it.targetEnd }.max()!!,
                targetStrand = mutableIntervals.first().targetStrand,
//                queryStart = mutableIntervals.minOf { it.queryStart },
                queryStart = mutableIntervals.map { it.queryStart }.min()!!,
//                queryEnd = mutableIntervals.maxOf { it.queryEnd },
                queryEnd = mutableIntervals.map { it.queryEnd }.max()!!,
//                numPositive = mutableIntervals.sumOf { it.numPositive }
                numPositive = mutableIntervals.map { it.numPositive }.sum()
            )

        for (i in 0 until sortedIntervals.size - 1) {
            if (nextPassesQueryFilter(sortedIntervals[i], sortedIntervals[i+1], maxQueryGap, maxQueryOverlap)) {
                clusterTemp.add(sortedIntervals[i+1])
            } else {
                clusterOutput.add(mergeCluster(clusterTemp))
                clusterTemp.clear()
                clusterTemp.add(sortedIntervals[i+1])
            }
        }
        clusterOutput.add(mergeCluster(clusterTemp))
        return clusterOutput
    }

    for (i in 0 until sortedResults.size - 1) {
        if (nextPassesTargetFilter(sortedResults[i], sortedResults[i+1], maxTargetGap, maxTargetOverlap)) {
            tempResults.add(sortedResults[i+1])
        } else {
            finalResults.addAll(processCluster(tempResults, maxQueryGap, maxQueryOverlap))
            tempResults.clear()
            tempResults.add(sortedResults[i+1])
        }
    }
    finalResults.addAll(processCluster(tempResults, maxQueryGap, maxQueryOverlap))
    return finalResults
}

val filename = "/Users/err87-admin/repos/appliedcentraldogma/test/3I2I5.minimal.tsv"
val blastResults = readBlastResults(File(filename))
blastResults.filter { it.evalue < 0.1 }.size

//blastResults.run {
//    System.err.println(joinToString("\n"))
//}
//blastResults
//    .groupBy {it.sseqid to it.sstrand}
//    .flatMap { (_, v) ->
//        mergeBlastResults(v, maxEvalue = 0.1, maxTargetOverlap = 10, maxTargetGap = 5_000, maxQueryOverlap = 5, maxQueryGap = 10)
//    }.also {
//        System.err.println(it.joinToString("\n"))
//    }


//val maxTargetGap = 1_000
//val maxTargetOverlap = 10
//val maxQueryOverlap = 10
//val maxEvalue = 0.1
//val mutableResults = blastResults
//    .filter { it.evalue < maxEvalue }
//    .map { with(it) {
//        MutableInterval(qseqid, sseqid, minOf(sstart, send), maxOf(sstart, send), sstrand, qstart, qend, positive) }
//    }
//    .sortedWith(compareBy({it.targetStrand}, {it.targetStart}, {it.targetEnd}))
//    .toMutableList()
//
////System.err.println(mutableResults.joinToString("\n"))
//val current = mutableResults.removeAt(0)
//System.err.println("current:\n$current")
//val temp = mutableListOf(current)
//mutableResults
//    .asSequence()
//    .filter { it.name == current.name }
//    .filter { it.chrom == current.chrom }
//    .filter { it.targetStrand == current.targetStrand }
//    .filter { it.targetEnd > current.targetEnd }
//    .filter { it.targetStart - current.targetEnd < maxTargetGap }
//    .filter { it.targetStart - current.targetEnd > -maxTargetOverlap }
//    .toCollection(temp)
////    .sortedBy { it.queryStart }
////    .filter { it.queryStart - current.queryEnd > -maxQueryOverlap }
////    .toList()
////    .also { System.err.println("filter:\n${it.joinToString("\n")}") }
//
//temp.sortedBy { it.queryStart }
//    .filter { it.queryStart - current.queryEnd > -maxQueryOverlap }
//    .toList()
//    .also { System.err.println("filter:\n${it.joinToString("\n")}") }

//System.err.println(mergedResults.joinToString("\n"))