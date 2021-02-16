package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable

@Serializable
data class MpWorkModeDto(
    val mode: MpWorkModeDto = null,
        ) {
    enum class WorkModeDto {
        PROD,
        TEST
    }
}
