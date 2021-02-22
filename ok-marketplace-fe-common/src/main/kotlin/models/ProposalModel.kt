package models

data class ProposalModel(
    override var id: models.ProposalIdModel = models.ProposalIdModel.Companion.NONE,
    override var avatar: String = "",
    override var title: String = "",
    override var description: String = "",
    override var linkView: String = "",
    override var linkEdit: String = "",
    override var linkDelete: String = "",
    override var tagIds: MutableSet<models.TagIdModel> = mutableSetOf(),
    override var tags: MutableSet<models.TagModel> = mutableSetOf(),
    override var techDets: MutableSet<models.TechDetModel> = mutableSetOf(),
): models.IMarketplaceItem {
    companion object {
        val NONE = ProposalModel()
    }
}
