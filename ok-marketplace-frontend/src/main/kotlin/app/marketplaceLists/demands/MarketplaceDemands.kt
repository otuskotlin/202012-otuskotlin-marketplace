package app.marketplaceLists.demands

import com.ccfraser.muirwik.components.gridlist.mGridList
import app.marketplaceLists.demands.demand.marketplaceDemand
import app.marketplaceLists.items.MarketplaceItems
import models.DemandIdModel
import models.DemandModel
import react.*
import styled.css
import styled.styledDiv

fun RBuilder.marketplaceDemands(handler: MarketplaceDemandsProps.() -> Unit = {}) = child(MarketplaceDemands::class) {
    attrs(handler)
}

class MarketplaceDemands : MarketplaceItems<MarketplaceDemandsProps, MarketplaceDemandsState>() {
    override fun RBuilder.render() {
        marketplaceItems {
            marketplaceDemand {
                attrs {
                    item = DemandModel(
                        id = DemandIdModel("demand-1")
                    )
                }
            }
            marketplaceDemand {
                attrs {
                    item = DemandModel(
                        id = DemandIdModel("demand-2")
                    )
                }
            }
            marketplaceDemand {
                attrs {
                    item = DemandModel(
                        id = DemandIdModel("demand-3")
                    )
                }
            }
        }
    }
}

