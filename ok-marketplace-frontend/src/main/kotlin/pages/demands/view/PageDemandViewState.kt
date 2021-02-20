package pages.demands.view

import models.DemandModel
import models.ProposalModel
import react.RState

interface PageDemandViewState: RState {
    var demand: DemandModel?
    var offers: List<ProposalModel>?
}
