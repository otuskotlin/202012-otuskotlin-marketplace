package layouts.headed

import MarketplaceDsl
import react.RBuilder

@MarketplaceDsl
class LayoutHeadedConf {
    var pageMainBody: RBuilder.() -> Unit = {}
        private set

    fun pageMainBody(block: RBuilder.() -> Unit) {
        pageMainBody = block
    }
}
