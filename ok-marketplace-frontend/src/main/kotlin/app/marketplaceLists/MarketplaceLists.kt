package app.marketplaceLists

import com.ccfraser.muirwik.components.*
import app.marketplaceLists.demands.marketplaceDemands
import app.marketplaceLists.proposals.marketplaceProposals
import react.*

class MarketplaceLists(props: MarketplaceListsProps) : RComponent<RProps, MarketplaceListsState>(props) {
    override fun RBuilder.render() {
        mGridContainer {
            attrs {
                alignItems = MGridAlignItems.stretch
                spacing = MGridSpacing.spacing0
            }
            mGridItem() {
                attrs {
                    xs = MGridSize.cells12
                    sm = MGridSize.cells12
                    md = MGridSize.cells5
                    lg = MGridSize.cells3
                }
                marketplaceDemands()
            }

            mGridItem() {
                attrs {
                    xs = MGridSize.cells12
                    sm = MGridSize.cells12
                    md = MGridSize.cells7
                    lg = MGridSize.cells9
                }
                marketplaceProposals {}
            }
        }
    }
}

//fun RBuilder.marketplaceLists(handler: MarketplaceListsProps.() -> Unit) = child(MarketplaceLists::class) {
//    attrs(handler)
//}
