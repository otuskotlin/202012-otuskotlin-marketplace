package ru.otus.otuskotlin.marketplace.transport.kmp.models.common



interface IMpRequest{
    val requestId: String?
    val onResponse: String?
    val startTime: String?
    val debug: IMpDebug?
}
