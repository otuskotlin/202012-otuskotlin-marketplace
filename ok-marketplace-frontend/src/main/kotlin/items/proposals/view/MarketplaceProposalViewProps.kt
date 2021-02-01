package items.proposals.view

import models.DemandModel
import models.ProposalModel
import react.RProps

data class MarketplaceProposalViewProps(
    val proposal: ProposalModel?,
    val demands: List<DemandModel>? = null
): RProps {
}
