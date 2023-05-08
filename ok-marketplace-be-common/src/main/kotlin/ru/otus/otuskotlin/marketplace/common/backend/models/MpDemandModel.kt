package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpDemandModel(
    override val id: MpDemandIdModel = MpDemandIdModel.NONE,
    override val avatar: String = "",
    override val title: String = "",
    override val description: String = "",
    override val owner: MpUserModel = MpUserModel.NONE,
    override val tagIds: MutableSet<String> = mutableSetOf(),
    override val techDets: MutableSet<MpTechDetModel> = mutableSetOf(),
): IMpItemModel {
    companion object {
        val NONE = MpDemandModel()
    }
}
