package items.demands.list

import items.demands.list.demand.marketplaceDemand
import items.base.list.MarketplaceItems
import models.DemandIdModel
import models.DemandModel
import react.*

fun RBuilder.marketplaceDemands(handler: MarketplaceDemandsProps.() -> Unit = {}) = child(MarketplaceDemands::class) {
    attrs(handler)
}

class MarketplaceDemands : MarketplaceItems<MarketplaceDemandsProps, MarketplaceDemandsState>() {
    override fun RBuilder.render() {
        marketplaceItems {
            marketplaceDemand {
                attrs {
                    item = DemandModel(
                        id = DemandIdModel("demand-1"),
                    )
                }
            }
            marketplaceDemand {
                attrs {
                    item = DemandModel(
                        id = DemandIdModel("demand-2"),
                    )
                }
            }
            marketplaceDemand {
                attrs {
                    item = DemandModel(
                        id = DemandIdModel("demand-3"),
                    )
                }
            }
        }
    }
}

