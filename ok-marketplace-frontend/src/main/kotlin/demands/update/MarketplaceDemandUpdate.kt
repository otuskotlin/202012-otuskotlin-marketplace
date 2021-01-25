package demands.update

import demands.MarketplaceDemandRouteProps
import items.update.MarketplaceUpdates
import react.RBuilder
import react.RState
import react.rClass
import react.router.dom.RouteResultProps

class MarketplaceDemandUpdate(props: MarketplaceDemandUpdateProps) : MarketplaceUpdates<MarketplaceDemandUpdateProps, RState>(props) {
    override fun RBuilder.render() {
        marketplaceUpdate {
            itemTitle = "Update demand: ${props.match.params.demandId}"
        }
    }
}

fun RBuilder.marketplaceDemandUpdate(props: RouteResultProps<MarketplaceDemandRouteProps>) =
    child(MarketplaceDemandUpdate::class.rClass, props = props) { }
