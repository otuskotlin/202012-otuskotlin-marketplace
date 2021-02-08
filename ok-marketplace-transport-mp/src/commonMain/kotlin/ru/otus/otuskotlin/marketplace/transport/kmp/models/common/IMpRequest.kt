package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

interface IMpRequest : IMpMessage {
    val onResponse: String?
    val startTime: String?
    val debug: DebugDto?
}
