package app.marketplaceLists.proposals.proposal

import app.marketplaceLists.items.item.IMarketplaceItemProps
import models.ProposalModel

interface MarketplaceProposalProps : IMarketplaceItemProps {
    override var item: ProposalModel
}
