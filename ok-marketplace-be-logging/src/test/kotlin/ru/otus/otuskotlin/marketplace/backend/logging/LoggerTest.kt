package ru.otus.otuskotlin.marketplace.backend.logging

import kotlinx.coroutines.runBlocking
import kotlin.test.Test

internal class LoggerTest {

    @Test
    fun loggerInit() {
        runBlocking{
            val logger = mpLogger(this::class.java)
            logger.doWithLoggingSusp(logId = "test-logger") {
                println("Some action")
            }
        }
    }
}
