package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable

@Serializable
class TagDto (
    val id: TagIdDto? = null,
    val title: String? = null,
    val avatar: TagAvatarDto? = null,
)
