package ru.otus.otuskotlin.marketplace.backend.common.models

inline class ModelTagId(val id: String) {
    companion object{
        val NONE = ModelTagId("")
    }
}
