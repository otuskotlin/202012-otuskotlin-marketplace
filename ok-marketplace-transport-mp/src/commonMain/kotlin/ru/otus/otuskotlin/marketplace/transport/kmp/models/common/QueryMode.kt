package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable

@Serializable
enum class QueryMode {
    PROD,
    TEST
}
