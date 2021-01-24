package app.marketplaceLists.proposals.proposal

import app.marketplaceLists.MarketplaceItem
import react.RBuilder
import react.RHandler
import react.router.dom.withRouter

class MarketplaceProposal(props: MarketplaceProposalProps) :
    MarketplaceItem<MarketplaceProposalProps, MarketplaceProposalState>(props) {

    private val proposal = props.item
    private val routeToView = "/proposal/${proposal.id}"
    private val routeToUpdate = "/proposal/${proposal.id}/edit"
    private val routeToDelete = "/proposal/${proposal.id}/del"

    override fun RBuilder.render() {
        marketplaceItem {
            itemTitle = "Some Proposal"
            itemViewRoute = routeToView
            addAction("edit", routeToUpdate)
            addAction("delete", routeToDelete)
        }
    }
}

fun RBuilder.marketplaceProposal(handler: RHandler<MarketplaceProposalProps>) =
    withRouter(MarketplaceProposal::class).invoke(handler)
