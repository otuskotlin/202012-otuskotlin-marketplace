package items.proposals.list

import items.base.list.MarketplaceItems
import items.proposals.list.proposal.marketplaceProposal
import models.ProposalIdModel
import models.ProposalModel
import react.*

fun RBuilder.marketplaceProposals(handler: MarketplaceProposalsProps.() -> Unit = {}) =
    child(MarketplaceProposals::class) {
        attrs(handler)
    }

class MarketplaceProposals : MarketplaceItems<MarketplaceProposalsProps, MarketplaceProposalsState>() {
    override fun RBuilder.render() {
        marketplaceItems {
            marketplaceProposal {
                attrs {
                    item = ProposalModel(
                        id = ProposalIdModel("proposal-1")
                    )
                }
            }
            marketplaceProposal {
                attrs {
                    item = ProposalModel(
                        id = ProposalIdModel("proposal-2")
                    )
                }
            }
            marketplaceProposal {
                attrs {
                    item = ProposalModel(
                        id = ProposalIdModel("proposal-3")
                    )
                }
            }
        }
    }
}
