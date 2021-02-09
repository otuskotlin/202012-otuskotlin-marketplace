package ru.otus.otuskotlin.marketplace.backend.common.models

inline class MpProposalIdModel(
    override val id: String

): IMpItemIdModel {
    companion object {
        val NONE = MpProposalIdModel("")
    }
}
