package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpProposalFilterModel(
    val text: String = "",
    val sortBy: MpSortModel = MpSortModel.NONE,
    val offset: Int = Int.MIN_VALUE,
    val count: Int = Int.MIN_VALUE,
) {
    companion object {
        val NONE = MpProposalFilterModel()
    }
}
