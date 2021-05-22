package ru.otus.otuskotlin.marketplace.transport.logs

data class ErrorLog(
    val message: String? = null,
    val stackTrace: String? = null,
)
