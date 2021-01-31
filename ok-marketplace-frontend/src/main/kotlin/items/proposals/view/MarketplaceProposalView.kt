package items.proposals.view

import items.base.view.marketplaceView
import react.RBuilder
import react.RComponent
import react.RState
import react.rClass

class MarketplaceProposalView(props: MarketplaceProposalViewProps) : RComponent<MarketplaceProposalViewProps, RState>(props) {
    override fun RBuilder.render() {
        val proposal = props.proposal
        console.log("MarketplaceProposalView", props, proposal)
        if (proposal != null) {
            marketplaceView() {
                item = proposal
            }
        }
    }
}

fun RBuilder.marketplaceProposalView(props: MarketplaceProposalViewProps) =
    child(MarketplaceProposalView::class.rClass, props = props) { }
