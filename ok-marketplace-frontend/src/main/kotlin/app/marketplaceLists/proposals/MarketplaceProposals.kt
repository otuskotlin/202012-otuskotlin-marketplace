package app.marketplaceLists.proposals

import com.ccfraser.muirwik.components.gridlist.mGridList
import app.marketplaceLists.proposals.proposal.marketplaceProposal
import react.*

fun RBuilder.marketplaceProposals(handler: MarketplaceProposalsProps.() -> Unit = {}) = child(MarketplaceProposals::class) {
    attrs(handler)
}

class MarketplaceProposals : RComponent<MarketplaceProposalsProps, MarketplaceProposalsState>() {
    override fun RBuilder.render() {
        mGridList {
            marketplaceProposal {}
            marketplaceProposal()
            marketplaceProposal()
            marketplaceProposal()
        }
    }

}
