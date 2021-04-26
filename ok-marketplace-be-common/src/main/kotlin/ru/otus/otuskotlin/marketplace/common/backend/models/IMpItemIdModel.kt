package ru.otus.otuskotlin.marketplace.common.backend.models

import java.util.*

interface IMpItemIdModel {
    val id: String

    fun asString() = id
    fun asUUID(): UUID = UUID.fromString(id)
}
