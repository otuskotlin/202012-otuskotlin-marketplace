package items.proposals.update

import MarketplaceDsl
import items.base.update.MarketplaceUpdates
import items.proposals.MarketplaceProposalRouteParams
import models.ProposalIdModel
import react.RBuilder
import react.RState
import react.rClass
import react.router.dom.RouteResultProps

@MarketplaceDsl
class MarketplaceProposalUpdate(props: MarketplaceProposalUpdateProps) : MarketplaceUpdates<MarketplaceProposalUpdateProps, RState>(props) {
    override fun RBuilder.render() {
        marketplaceUpdate {
            itemTitle = "Update proposal: ${props.match.params.proposalId}"
        }
    }

    companion object {
        const val linkMask = "/proposal/:proposalId/edit"
        fun makeLink(id: String) = "/proposal/$id/edit"
        fun makeLink(id: ProposalIdModel) = makeLink(id.id)
    }
}

fun RBuilder.marketplaceProposalUpdate(props: RouteResultProps<MarketplaceProposalRouteParams>) =
    child(MarketplaceProposalUpdate::class.rClass, props = props) { }
