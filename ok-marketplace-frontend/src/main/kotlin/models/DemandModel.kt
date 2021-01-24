package models

data class DemandModel(
    override var id: DemandIdModel = DemandIdModel.NONE
): IMarketplaceItem
