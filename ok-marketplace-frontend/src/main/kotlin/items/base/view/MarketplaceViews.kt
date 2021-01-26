package items.base.view

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1

abstract class MarketplaceViews<P: RProps,S: RState>(props: P) : RComponent<P,S>() {
    fun RBuilder.marketplaceView(conf: MarketplaceViewConf.() -> Unit) {
        val viewConf = MarketplaceViewConf().apply(conf)
        h1 {
            +"Views ${viewConf.itemTitle}"
        }
    }

    class MarketplaceViewConf {
        var itemTitle: String = ""
    }
}
