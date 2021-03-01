package items.proposals.list.proposal

import items.base.list.IMarketplaceItemProps
import models.ProposalModel

interface MarketplaceProposalProps : IMarketplaceItemProps {
    override var item: ProposalModel
}
