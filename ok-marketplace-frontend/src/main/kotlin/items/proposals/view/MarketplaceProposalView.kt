package items.proposals.view

import items.base.view.MarketplaceViews
import items.proposals.MarketplaceProposalRouteParams
import react.RBuilder
import react.RState
import react.rClass
import react.router.dom.RouteResultProps

class MarketplaceProposalView(props: MarketplaceProposalViewProps) : MarketplaceViews<MarketplaceProposalViewProps, RState>(props) {
    override fun RBuilder.render() {
        marketplaceView {
            itemTitle = "View proposal: ${props.match.params.proposalId}"
        }
    }
}

fun RBuilder.marketplaceProposalView(props: RouteResultProps<MarketplaceProposalRouteParams>) =
    child(MarketplaceProposalView::class.rClass, props = props) { }