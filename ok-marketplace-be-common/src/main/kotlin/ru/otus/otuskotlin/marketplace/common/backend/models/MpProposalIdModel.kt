package ru.otus.otuskotlin.marketplace.common.backend.models

inline class MpProposalIdModel(
    override val id: String
): IMpItemIdModel {

    companion object {
        val NONE = MpProposalIdModel("")
    }
}
