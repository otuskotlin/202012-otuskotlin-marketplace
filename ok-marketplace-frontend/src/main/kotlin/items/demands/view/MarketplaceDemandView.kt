package items.demands.view

import MarketplaceDsl
import items.demands.MarketplaceDemandRouteParams
import items.base.view.MarketplaceViews
import react.RBuilder
import react.RState
import react.rClass
import react.router.dom.RouteResultProps

@MarketplaceDsl
class MarketplaceDemandView(props: MarketplaceDemandViewProps) : MarketplaceViews<MarketplaceDemandViewProps, RState>(props) {
    override fun RBuilder.render() {
        marketplaceView {
            itemTitle = "View demand: ${props.match.params.demandId}"
        }
    }
}

fun RBuilder.marketplaceDemandView(props: RouteResultProps<MarketplaceDemandRouteParams>) =
    child(MarketplaceDemandView::class.rClass, props = props) { }
