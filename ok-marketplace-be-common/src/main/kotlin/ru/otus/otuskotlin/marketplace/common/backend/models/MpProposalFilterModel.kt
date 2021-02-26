package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpProposalFilterModel(
    val text: String = ""
) {
    companion object {
        val NONE = MpProposalFilterModel()
    }
}
