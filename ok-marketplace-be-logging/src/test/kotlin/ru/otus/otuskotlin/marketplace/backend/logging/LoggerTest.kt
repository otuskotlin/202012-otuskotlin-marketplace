package ru.otus.otuskotlin.marketplace.backend.logging

import kotlin.test.Test

internal class LoggerTest {

    @Test
    fun loggerInit() {
        val logger = mpLogger(this::class.java)
        logger.doWithLogging(logId = "test-logger") {
            println("Some action")
        }
    }
}
