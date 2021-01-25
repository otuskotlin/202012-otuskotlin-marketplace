package proposals.update

import items.update.MarketplaceUpdates
import proposals.MarketplaceProposalRouteParams
import react.RBuilder
import react.RState
import react.rClass
import react.router.dom.RouteResultProps

class MarketplaceProposalUpdate(props: MarketplaceProposalUpdateProps) : MarketplaceUpdates<MarketplaceProposalUpdateProps, RState>(props) {
    override fun RBuilder.render() {
        marketplaceUpdate {
            itemTitle = "Update proposal: ${props.match.params.proposalId}"
        }
    }
}

fun RBuilder.marketplaceProposalUpdate(props: RouteResultProps<MarketplaceProposalRouteParams>) =
    child(MarketplaceProposalUpdate::class.rClass, props = props) { }
