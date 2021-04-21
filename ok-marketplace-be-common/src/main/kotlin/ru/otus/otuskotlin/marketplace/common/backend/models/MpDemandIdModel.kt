package ru.otus.otuskotlin.marketplace.common.backend.models

import java.util.*

inline class MpDemandIdModel(
    override val id: String
): IMpItemIdModel {

    fun asString() = id
    fun asUUID(): UUID = UUID.fromString(id)

    companion object {
        val NONE = MpDemandIdModel("")
    }
}
