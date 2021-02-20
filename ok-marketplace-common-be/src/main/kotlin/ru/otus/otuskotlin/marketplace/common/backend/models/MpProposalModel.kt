package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpProposalModel(
    override val id: MpProposalIdModel = MpProposalIdModel.NONE,
    override val avatar: String = "",
    override val title: String = "",
    override val description: String = "",
    override val tagIds: MutableSet<String> = mutableSetOf(),
    override val techDets: MutableSet<MpTechDetModel> = mutableSetOf(),
): IMpItemModel {
    companion object {
        val NONE = MpProposalModel()
    }
}
