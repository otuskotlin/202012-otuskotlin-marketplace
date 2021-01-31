package models

data class DemandModel(
    override var id: DemandIdModel = DemandIdModel.NONE,
    override val avatar: String = "",
    override val title: String = "",
    override val linkView: String = "",
    override val linkEdit: String = "",
    override val linkDelete: String = "",
    override val tags: MutableSet<TagModel> = mutableSetOf(),
    override val techDets: MutableSet<TechDetModel> = mutableSetOf(),
): IMarketplaceItem
