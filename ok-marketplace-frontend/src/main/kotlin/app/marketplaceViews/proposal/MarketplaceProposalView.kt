package app.marketplaceViews.proposal

import app.marketplaceViews.MarketplaceViews
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.h1
import react.rClass
import react.router.dom.RouteResultProps

class MarketplaceProposalView(props: MarketplaceProposalViewProps) : MarketplaceViews<MarketplaceProposalViewProps, RState>(props) {
    override fun RBuilder.render() {
        marketplaceView {
            itemTitle = "View proposal: ${props.match.params.proposalId}"
        }
    }
}

fun RBuilder.marketplaceProposalView(props: RouteResultProps<MarketplaceProposalViewRequest>) =
    child(MarketplaceProposalView::class.rClass, props = props) { }
