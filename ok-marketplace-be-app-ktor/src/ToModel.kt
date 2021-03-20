package ru.otus.otuskotlin.marketplace.backend.app.ktor

import kotlinx.serialization.SerializationException
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.marketplace.common.backend.models.MpError

private val logger = LoggerFactory.getLogger("Throwable.toModel")
fun Throwable.toModel(): MpError = when (this) {
    is SerializationException -> MpError(message = "Request JSON syntax error: ${this.message}")
    else -> {
        logger.error("Unknown exception", this)
        MpError(message = "Some exception is thrown: ${this.message}")
    }
}
