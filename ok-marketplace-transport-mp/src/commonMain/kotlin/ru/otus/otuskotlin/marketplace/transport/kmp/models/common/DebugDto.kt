package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable

@Serializable
data class DebugDto (
    val mode: WorkModeDto? = null,
        ) {
    enum class WorkModeDto {
        PROD,
        TEST
    }
}
