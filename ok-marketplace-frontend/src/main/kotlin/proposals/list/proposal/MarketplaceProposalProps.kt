package proposals.list.proposal

import items.list.IMarketplaceItemProps
import models.ProposalModel

interface MarketplaceProposalProps : IMarketplaceItemProps {
    override var item: ProposalModel
}
