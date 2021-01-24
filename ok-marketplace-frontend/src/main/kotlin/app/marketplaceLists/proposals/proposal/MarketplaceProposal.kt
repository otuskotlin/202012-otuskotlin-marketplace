package app.marketplaceLists.proposals.proposal

import com.ccfraser.muirwik.components.list.mListItem
import react.*

class MarketplaceProposal : RComponent<MarketplaceProposalProps, MarketplaceProposalState>() {
    override fun RBuilder.render() {
        mListItem {
            +"List Item"
        }
    }

}

fun RBuilder.marketplaceProposal(handler: MarketplaceProposalProps.() -> Unit = {}) = child(MarketplaceProposal::class) {
    attrs(handler)
}
