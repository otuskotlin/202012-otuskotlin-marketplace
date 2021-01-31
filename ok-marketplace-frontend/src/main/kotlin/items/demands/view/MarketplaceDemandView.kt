package items.demands.view

import MarketplaceDsl
import items.base.view.marketplaceView
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
}

fun RBuilder.marketplaceDemandView(props: MarketplaceDemandViewProps): ReactElement {
    console.log("RBuilder.marketplaceDemandView", props)
    return child(MarketplaceDemandView::class.rClass, props = props) { }
}
