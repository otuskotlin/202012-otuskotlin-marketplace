package ru.otus.otuskotlin.marketplace.common.backend.models

inline class MpDemandIdModel(
    override val id: String
): IMpItemIdModel {

    fun asString() = id

    companion object {
        val NONE = MpDemandIdModel("")
    }
}
