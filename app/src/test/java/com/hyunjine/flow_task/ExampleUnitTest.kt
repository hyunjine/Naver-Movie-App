package com.hyunjine.flow_task

import androidx.annotation.UiThread
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun mains() {
        runBlocking {
            val a = launch {
                for (i in 1..5) {
                    println(i)
                    delay(500L)
                }
            }

            val b = async {
                "async 종료"
            }
            println("async 대기")
            println(b.await())

            println("launch 대기")
            a.join()
            println("launch 대기")

//            val lineUp = launch {
//                coroutineLinedUp()
//            }
//
//            val playMusicWithLinedUp = launch {
//                coroutinePlayMusic()
//            }
//
//            lineUp.join()
//            playMusicWithLinedUp.cancel()
//            coroutineTicketing()
//
//            val waitingBus = launch {
//                coroutineWaitingTheBus()
//            }
//
//            val playMusicWithWaitingBus = launch {
//                coroutinePlayMusic()
//            }
//
//            waitingBus.join()
//            playMusicWithWaitingBus.cancel()
//            coroutineTakeTheBus()
        }
    }

    suspend fun coroutineLinedUp() {
        println("lined up")
        delay(2000)
    }

    fun coroutineTicketing() {
        println("ticketing")
    }

    suspend fun coroutineWaitingTheBus() {
        println("waiting the bus")
        delay(2000)
    }

    fun coroutineTakeTheBus() {
        println("take the bus")
    }

    suspend fun coroutinePlayMusic() {
        println("play music")
        while(true) {
            println("listening..")
            delay(500)
        }
    }
}