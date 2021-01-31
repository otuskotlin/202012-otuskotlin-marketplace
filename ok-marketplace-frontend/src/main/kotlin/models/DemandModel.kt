package models

data class DemandModel(
    override var id: DemandIdModel = DemandIdModel.NONE,
    override val avatar: String = ""
): IMarketplaceItem
