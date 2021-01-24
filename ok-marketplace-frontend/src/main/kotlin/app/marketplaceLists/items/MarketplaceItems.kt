package app.marketplaceLists.items

import app.marketplaceLists.demands.demand.marketplaceDemand
import app.marketplaceLists.items.item.MarketplaceItem
import com.ccfraser.muirwik.components.gridlist.mGridList
import models.DemandIdModel
import models.DemandModel
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledDiv

abstract class MarketplaceItems<P: RProps,S: RState>: RComponent<P,S>() {
    fun RBuilder.marketplaceItems(conf: RBuilder.() -> Unit) {
        styledDiv {
            css {
//                borderColor = Color.red
//                borderStyle = BorderStyle.solid
            }
            mGridList(cols = 1) {
                conf()
            }
        }
    }

}

