package pages.home

import items.demands.list.marketplaceDemands
import items.proposals.list.marketplaceProposals
import layouts.columned.layoutColumned
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

class PageHome: RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        layoutColumned {
            demandsBlock {
                marketplaceDemands {  }
            }

            proposalsBlock {
                marketplaceProposals {  }
            }
        }
    }
}
