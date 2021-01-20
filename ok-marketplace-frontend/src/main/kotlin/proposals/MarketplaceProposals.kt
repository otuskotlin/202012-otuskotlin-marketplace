package proposals

import com.ccfraser.muirwik.components.gridlist.mGridList
import proposals.proposal.marketplaceProposal
import react.*

fun RBuilder.marketplaceProposals(props: MarketplaceProposalsProps = MarketplaceProposalsProps()) =
    child(MarketplaceProposals::class.rClass, props = props) { }

class MarketplaceProposals : RComponent<MarketplaceProposalsProps, MarketplaceProposalsState>() {
    override fun RBuilder.render() {
        mGridList {
            marketplaceProposal()
            marketplaceProposal()
            marketplaceProposal()
            marketplaceProposal()
        }
    }

}

class MarketplaceProposalsState : RState {

}

class MarketplaceProposalsProps : RProps {

}
