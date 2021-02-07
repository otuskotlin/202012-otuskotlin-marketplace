package ru.otus.otuskotlin.marketplace.transport.kmp.models.common


interface IMpResponse : IMpMessage{
    val onRequest: String?
    val endTime: String?
    val errors: List<ErrorDto>?
    val status: ResponseStatusDto?
}

