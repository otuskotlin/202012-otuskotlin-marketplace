package demands.view

import demands.MarketplaceDemandRouteProps
import items.view.MarketplaceViews
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

fun RBuilder.marketplaceDemandView(props: RouteResultProps<MarketplaceDemandRouteProps>) =
    child(MarketplaceDemandView::class.rClass, props = props) { }
