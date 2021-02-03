package ru.otus.otuskotlin.marketplace.backend.common.models

inline class ModelItemId(val id: String) {
    companion object{
        val NONE = ModelItemId("")
    }
}
