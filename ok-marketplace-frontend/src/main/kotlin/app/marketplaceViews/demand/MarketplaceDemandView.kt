package app.marketplaceViews.demand

import app.marketplaceViews.MarketplaceViews
import react.RBuilder
import react.RState
import react.rClass
import react.router.dom.RouteResultProps

class MarketplaceDemandView(props: MarketplaceDemandViewProps) : MarketplaceViews<MarketplaceDemandViewProps, RState>(props) {
    override fun RBuilder.render() {
        marketplaceView {
            itemTitle = "View demand: ${props.match.params.demandId}"
        }
    }
}

fun RBuilder.marketplaceDemandView(props: RouteResultProps<MarketplaceDemandViewRequest>) =
    child(MarketplaceDemandView::class.rClass, props = props) { }
