package net.maizegenetics.gasco

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach

object ParallelManager {
    fun <T, R> run(input: Iterable<T>, threads: Int, inputOper: (T) -> R): Iterable<R> = runBlocking {
        val inputChannel = Channel<T>()
        val outputChannel = Channel<R>()
        val actualThreads = threads.coerceIn(1, Runtime.getRuntime().availableProcessors())
        launch {
            input.forEach { inputChannel.send(it) }
            inputChannel.close()
        }
        val workers = (1..actualThreads).map {
            launch {
                for (item in inputChannel) {
                    outputChannel.send(inputOper(item))
                }
            }
        }
        val results = mutableListOf<R>()
        val collector = launch {
            outputChannel.consumeEach { results.add(it) }
        }
        launch {
            workers.joinAll()
            outputChannel.close()
            collector.join()
        }
        results
    }
}

//class Parallelizer<T, R>(val input: Iterable<T>, threads: Int, val inputOper: (T) -> R, val outputOper: (R) -> Unit) {
//
//    val inputChannel = Channel<T>()
//    val outputChannel = Channel<R>()
//    val threads: Int = threads.coerceIn(1, Runtime.getRuntime().availableProcessors())
//
//    private suspend fun populate() {
//        for (item in input) {
//            inputChannel.send(item)
//        }
//        inputChannel.close()
//    }
//
//    private suspend fun worker() {
//        for (item in inputChannel) {
//            outputChannel.send(inputOper(item))
//        }
//    }
//
//    private suspend fun collect() {
//        for (item in outputChannel) {
//            outputOper(item)
//        }
//    }
//
//    fun run() = runBlocking {
//        launch { populate() }
//        val workers = (1..threads).map { launch { worker() } }
//        val collector = launch { collect() }
//        launch {
//            workers.joinAll()
//            outputChannel.close()
//            collector.join()
//        }
//    }
//}
