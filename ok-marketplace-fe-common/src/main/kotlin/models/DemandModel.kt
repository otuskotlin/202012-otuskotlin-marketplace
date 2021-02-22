package models

data class DemandModel(
    override var id: DemandIdModel = DemandIdModel.NONE,
    override var avatar: String = "",
    override var title: String = "",
    override var description: String = "",
    override var linkView: String = "",
    override var linkEdit: String = "",
    override var linkDelete: String = "",
    override var tagIds: MutableSet<TagIdModel> = mutableSetOf(),
    override var tags: MutableSet<TagModel> = mutableSetOf(),
    override var techDets: MutableSet<TechDetModel> = mutableSetOf(),
): models.IMarketplaceItem {
    companion object {
        val NONE = DemandModel()
    }
}
