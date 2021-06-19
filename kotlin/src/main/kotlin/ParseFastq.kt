import java.io.File

val fastqPath = "/Users/err87-admin/repos/scratch/test.fq"
val fastqFile = File(fastqPath)

fun main() {
    fastqFile.bufferedReader().use { reader ->
        reader.readLines().forEach { line ->
            System.out.println(line)
        }
    }
}
