package ru.otus.otuskotlin.marketplace.transport.kmp.models.requests

import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.QueryMode

interface IRequestMP<T> {
    val requestId: String?
    val mode: QueryMode?
    val requestData: T?
}
