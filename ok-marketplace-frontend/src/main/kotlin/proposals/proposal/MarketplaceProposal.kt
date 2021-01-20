package proposals.proposal

import com.ccfraser.muirwik.components.list.mListItem
import react.*

class MarketplaceProposal : RComponent<MarketplaceProposalProps, MarketplaceProposalState>() {
    override fun RBuilder.render() {
        mListItem {
            +"List Item"
        }
    }

}

class MarketplaceProposalState : RState {

}

class MarketplaceProposalProps : RProps {

}

fun RBuilder.marketplaceProposal(props: MarketplaceProposalProps = MarketplaceProposalProps()) =
    child(MarketplaceProposal::class.rClass, props = props) { }
