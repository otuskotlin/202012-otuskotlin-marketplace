package items.demands.view

import MarketplaceDsl
import items.base.view.marketplaceView
import models.DemandIdModel
import react.*

@MarketplaceDsl
class MarketplaceDemandView(props: MarketplaceDemandViewProps): RComponent<MarketplaceDemandViewProps, RState>(props) {
    override fun RBuilder.render() {
        val demand = props.demand
        console.log("MarketplaceDemandView", props, demand)
        if (demand != null) {
            marketplaceView() {
                item = demand
            }
        }
    }

    companion object {
        const val linkMask = "/demand/:demandId"
        fun makeLink(id: String) = "/demand/$id"
        fun makeLink(id: DemandIdModel) = makeLink(id.id)
    }
}

fun RBuilder.marketplaceDemandView(props: MarketplaceDemandViewProps): ReactElement {
    console.log("RBuilder.marketplaceDemandView", props)
    return child(MarketplaceDemandView::class.rClass, props = props) { }
}
