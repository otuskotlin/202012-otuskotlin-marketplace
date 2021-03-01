package layouts.columned

import MarketplaceDsl
import react.RBuilder

@MarketplaceDsl
class LayoutColumnedConf {
    var demands: RBuilder.() -> Unit = {}
    var proposals: RBuilder.() -> Unit = {}

    fun demandsBlock(block: RBuilder.() -> Unit) {
        demands = block
    }

    fun proposalsBlock(block: RBuilder.() -> Unit) {
        proposals = block
    }
}
