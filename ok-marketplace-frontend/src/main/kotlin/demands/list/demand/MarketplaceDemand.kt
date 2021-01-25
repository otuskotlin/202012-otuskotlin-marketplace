package demands.list.demand

import items.list.MarketplaceItem
import react.RBuilder
import react.RHandler
import react.router.dom.withRouter

class MarketplaceDemand(props: MarketplaceDemandProps) :
    MarketplaceItem<MarketplaceDemandProps, MarketplaceDemandState>(props) {

    private val demand = props.item
    private val routeToView = "/demand/${demand.id}"
    private val routeToUpdate = "/demand/${demand.id}/edit"
    private val routeToDelete = "/demand/${demand.id}/del"

    override fun RBuilder.render() {
        marketplaceItem {
            itemTitle = "Some Demand"
            itemViewRoute = routeToView
            addAction("edit", routeToUpdate)
            addAction("delete", routeToDelete)
        }
    }
}

fun RBuilder.marketplaceDemand(handler: RHandler<MarketplaceDemandProps>) =
    withRouter(MarketplaceDemand::class).invoke(handler)
