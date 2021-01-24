package app

import com.ccfraser.muirwik.components.*
import app.header.marketplaceHeader
import app.marketplaceDemandView.MarketplaceDemandView
//import app.marketplaceDemandView.MarketplaceDemandView
//import app.marketplaceDemandView.marketplaceDemandView
import app.marketplaceLists.MarketplaceLists
//import app.marketplaceLists.marketplaceLists
import kotlinx.css.*
import react.*
import react.router.dom.browserRouter
import react.router.dom.hashRouter
import react.router.dom.route
import react.router.dom.switch
import styled.css

class Marketplace(props: MarketplaceProps) : RComponent<MarketplaceProps, MarketplaceState>(props) {
    override fun RBuilder.render() {
        mGridContainer {
            attrs {
                spacing = MGridSpacing.spacing0
                alignItems = MGridAlignItems.stretch
                justify = MGridJustify.flexStart
                direction = MGridDirection.column
            }
            mGridItem(xs = MGridSize.cells12) {
                marketplaceHeader()
            }
            mGridItem {
                css {
                    margin(all = 0.px)
                    padding(all = 0.px)
                    width = 100.vw
//                    height = 100.pc
                    overflow = Overflow.hidden
                }

                hashRouter {
                    switch {
                        route("/", exact = true, component = MarketplaceLists::class)
                        route("/demand", component = MarketplaceDemandView::class)
                    }
                }
            }
        }
    }

}

fun RBuilder.marketplace(handler: MarketplaceProps.() -> Unit) = child(Marketplace::class) {
    attrs(handler)
}
