package items.demands.update

import items.demands.MarketplaceDemandRouteParams
import items.base.update.MarketplaceUpdates
import models.DemandIdModel
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

    companion object {
        const val linkMask = "/demand/:demandId/edit"
        fun makeLink(id: String) = "/demand/$id/edit"
        fun makeLink(id: DemandIdModel) = makeLink(id.id)
    }
}

fun RBuilder.marketplaceDemandUpdate(props: RouteResultProps<MarketplaceDemandRouteParams>) =
    child(MarketplaceDemandUpdate::class.rClass, props = props) { }
