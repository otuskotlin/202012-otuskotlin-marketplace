package app

import com.ccfraser.muirwik.components.*
import app.header.marketplaceHeader
import app.marketplaceViews.MarketplaceViews
//import app.marketplaceDemandView.MarketplaceDemandView
//import app.marketplaceDemandView.marketplaceDemandView
import app.marketplaceLists.MarketplaceLists
import app.marketplaceViews.demand.MarketplaceDemandView
import app.marketplaceViews.demand.MarketplaceDemandViewProps
import app.marketplaceViews.demand.MarketplaceDemandViewRequest
import app.marketplaceViews.demand.marketplaceDemandView
import app.marketplaceViews.proposal.MarketplaceProposalViewRequest
import app.marketplaceViews.proposal.marketplaceProposalView
//import app.marketplaceLists.marketplaceLists
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
                        route<MarketplaceDemandViewRequest>("/demand/:demandId") { request ->
                            marketplaceDemandView(props = request)
                        }
                        route<MarketplaceProposalViewRequest>("/proposal/:proposalId") { request ->
                            marketplaceProposalView(props = request)
                        }
                    }
                }
            }
        }
    }

}

fun RBuilder.marketplace(handler: MarketplaceProps.() -> Unit) = child(Marketplace::class) {
    attrs(handler)
}
