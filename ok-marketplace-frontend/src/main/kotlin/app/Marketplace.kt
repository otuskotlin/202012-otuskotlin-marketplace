package app

import com.ccfraser.muirwik.components.*
import layouts.header.marketplaceHeader
import app.marketplaceLists.MarketplaceLists
import items.demands.MarketplaceDemandRouteParams
import items.demands.view.marketplaceDemandView
import kotlinx.css.*
import react.*
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
                    overflow = Overflow.hidden
                }

                hashRouter {
                    switch {
                        route("/", exact = true, component = MarketplaceLists::class)
                        route<MarketplaceDemandRouteParams>("/demand/:demandId", exact = true) { request ->
                            marketplaceDemandView(props = request)
                        }
//                        route<MarketplaceDemandRouteProps>("/demand/:demandId/edit", exact = true) { request ->
//                            marketplaceDemandUpdate(props = request)
//                        }
//                        route<MarketplaceProposalRouteParams>("/proposal/:proposalId", exact = true) { request ->
//                            marketplaceProposalView(props = request)
//                        }
//                        route<MarketplaceProposalRouteParams>("/proposal/:proposalId/edit", exact = true) { request ->
//                            marketplaceProposalUpdate(props = request)
//                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.marketplace(handler: MarketplaceProps.() -> Unit) = child(Marketplace::class) {
    attrs(handler)
}
