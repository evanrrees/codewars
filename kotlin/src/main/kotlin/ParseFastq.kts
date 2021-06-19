import java.io.File

val fastqPath = "/Users/err87-admin/repos/scratch/test.fq"
val fastqFile = File(fastqPath)

fastqFile.bufferedReader().use { reader ->
    reader.readLines().filter { it.startsWith('@') }.forEach { line ->
        System.out.println(line.replace("@", ""))
    }
}
