package items.proposals.view

import items.base.view.marketplaceView
import models.ProposalIdModel
import models.ProposalModel
import react.RBuilder
import react.RComponent
import react.RState
import react.rClass

class MarketplaceProposalView(props: MarketplaceProposalViewProps) : RComponent<MarketplaceProposalViewProps, RState>(props) {
    override fun RBuilder.render() {
        val proposal = props.proposal
        val demands = props.demands
        console.log("MarketplaceProposalView", props, proposal)
        if (proposal != null) {
            marketplaceView() {
                item = proposal
                offers = demands
            }
        }
    }

    companion object {
        const val linkMask = "/proposal/:proposalId"
        fun makeLink(id: String) = "/proposal/$id"
        fun makeLink(id: ProposalIdModel) = makeLink(id.id)
    }
}

fun RBuilder.marketplaceProposalView(props: MarketplaceProposalViewProps) =
    child(MarketplaceProposalView::class.rClass, props = props) { }
