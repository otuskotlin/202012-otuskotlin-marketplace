package app.marketplaceDemandView

import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.h1

class MarketplaceDemandView(props: MarketplaceDemandViewProps) :
    RComponent<RProps, MarketplaceDemandViewState>() {
    override fun RBuilder.render() {
        h1 {
            +"Demand View"
        }
    }
}

//fun RBuilder.marketplaceDemandView(handler: MarketplaceDemandViewProps.() -> Unit) =
//    child(MarketplaceDemandView::class) {
//        attrs(handler)
//    }
