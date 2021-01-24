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
                    sm = MGridSize.cells6
                    md = MGridSize.cells6
                    lg = MGridSize.cells6
                }
                marketplaceDemands()
            }

            mGridItem() {
                attrs {
                    xs = MGridSize.cells12
                    sm = MGridSize.cells6
                    md = MGridSize.cells6
                    lg = MGridSize.cells6
                }
                marketplaceProposals {}
            }
        }
    }
}

//fun RBuilder.marketplaceLists(handler: MarketplaceListsProps.() -> Unit) = child(MarketplaceLists::class) {
//    attrs(handler)
//}
