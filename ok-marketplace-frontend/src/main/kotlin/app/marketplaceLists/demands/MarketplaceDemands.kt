package app.marketplaceLists.demands

import com.ccfraser.muirwik.components.gridlist.mGridList
import app.marketplaceLists.demands.demand.marketplaceDemand
import com.ccfraser.muirwik.components.card.mCard
import react.*
import styled.css
import styled.styledDiv

fun RBuilder.marketplaceDemands(handler: MarketplaceDemandsProps.() -> Unit = {}) = child(MarketplaceDemands::class) {
    attrs(handler)
}

class MarketplaceDemands: RComponent<MarketplaceDemandsProps, MarketplaceDemandsState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
//                borderColor = Color.red
//                borderStyle = BorderStyle.solid
            }
            mGridList(cols = 1) {
                marketplaceDemand {}
                marketplaceDemand {}
                marketplaceDemand {}
            }
        }
    }

}

