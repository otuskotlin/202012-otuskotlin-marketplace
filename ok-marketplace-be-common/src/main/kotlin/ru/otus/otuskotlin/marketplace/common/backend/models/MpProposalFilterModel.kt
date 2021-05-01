package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpProposalFilterModel(
    override val text: String = "",
    override val includeDescription: Boolean = false,
    override val sortBy: MpSortModel = MpSortModel.NONE,
    override val offset: Int = Int.MIN_VALUE,
    override val count: Int = Int.MIN_VALUE,
): IMpItemFilterModel {
    companion object {
        val NONE = MpProposalFilterModel()
    }
}
