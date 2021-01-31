package pages.proposals.view

import models.ProposalModel
import react.RState

interface PageProposalViewState: RState {
    var proposal: ProposalModel?
}
