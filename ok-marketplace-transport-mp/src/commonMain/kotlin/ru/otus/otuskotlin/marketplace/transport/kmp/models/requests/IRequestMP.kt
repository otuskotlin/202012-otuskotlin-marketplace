package ru.otus.otuskotlin.marketplace.transport.kmp.models.requests

import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto

interface IRequestMP {
    val type: String?
    val id: String?
    val onResponse: String?
    val startTime: String?
    val debug: DebugDto?
}
