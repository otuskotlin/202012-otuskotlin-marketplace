package items.update

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1

abstract class MarketplaceUpdates<P: RProps,S: RState>(props: P) : RComponent<P,S>() {
    fun RBuilder.marketplaceUpdate(conf: MarketplaceUpdateConf.() -> Unit) {
        val updateConf = MarketplaceUpdateConf().apply(conf)
        h1 {
            +"Views ${updateConf.itemTitle}"
        }
    }

    class MarketplaceUpdateConf {
        var itemTitle: String = ""
    }
}
