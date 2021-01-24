package models

data class ProposalModel(
    override var id: ProposalIdModel = ProposalIdModel.NONE
): IMarketplaceItem {
}
