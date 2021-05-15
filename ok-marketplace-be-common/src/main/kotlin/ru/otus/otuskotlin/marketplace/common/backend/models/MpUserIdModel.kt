package ru.otus.otuskotlin.marketplace.common.backend.models

inline class MpUserIdModel(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MpUserIdModel("")
    }
}
