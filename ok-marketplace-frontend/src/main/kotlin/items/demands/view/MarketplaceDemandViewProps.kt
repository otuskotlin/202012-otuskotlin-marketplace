package items.demands.view

import models.DemandModel
import models.ProposalModel
import react.RProps

data class MarketplaceDemandViewProps(
    var demand: DemandModel?,
    var proposals: List<ProposalModel>? = null
): RProps {
}
