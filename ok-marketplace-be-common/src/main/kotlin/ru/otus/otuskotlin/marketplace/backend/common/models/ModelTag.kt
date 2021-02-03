package ru.otus.otuskotlin.marketplace.backend.common.models

data class ModelTag(
    val id: ModelTagId = ModelTagId.NONE,
    val title: String = "",
    val avatar: ModelTagAvatar = ModelTagAvatar.NONE,
) {
    companion object{
        val NONE = ModelTag()
    }
}
