package ru.otus.otuskotlin.marketplace.common.backend.models

import java.lang.IllegalArgumentException
import java.util.*

inline class MpDemandIdModel(
    override val id: String
): IMpItemIdModel {

    fun asString() = id
    fun asUUID(): UUID = UUID.fromString(id)
    fun asUUIDOrNull(): UUID? = try { UUID.fromString(id) } catch (e: IllegalArgumentException) { null }

    companion object {
        val NONE = MpDemandIdModel("")
    }
}
