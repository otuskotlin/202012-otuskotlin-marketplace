package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable

@Serializable
data class TagAvatarDto (
        val src: String? = null,
        val type: TagAvatarTypeDto? = null,
) {
    @Serializable
    enum class TagAvatarTypeDto {
        ICON,
        IMAGE
    }

}
