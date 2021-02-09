package ru.otus.otuskotlin.marketplace.backend.common.models

inline class MpDemandIdModel(
    override val id: String
): IMpItemIdModel {
    companion object {
        val NONE = MpDemandIdModel("")
    }
}
