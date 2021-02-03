package ru.otus.otuskotlin.marketplace.backend.common.models

data class ModelTagAvatar(
    val src: String = "",
    val type: TagAvatarType = TagAvatarType.NONE,
) {
    enum class TagAvatarType {
        NONE,
        ICON,
        IMAGE
    }

    companion object {
        val NONE = ModelTagAvatar("", TagAvatarType.NONE)
    }
}
