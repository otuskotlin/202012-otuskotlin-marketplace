package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable

@Serializable
enum class ResponseStatusDto {
    SUCCESS,
    BAD_REQUEST,
    INTERNAL_SERVER_ERROR,
    NOT_FOUND,
}
