package pages.proposals.view

import models.DemandModel
import models.ProposalModel
import react.RState

interface PageProposalViewState: RState {
    var proposal: ProposalModel?
    var offers: List<DemandModel>?
}
