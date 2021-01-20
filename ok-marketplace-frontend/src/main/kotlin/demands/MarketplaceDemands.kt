package demands

import com.ccfraser.muirwik.components.gridlist.mGridList
import demands.demand.marketplaceDemand
import react.*
import styled.css
import styled.styledDiv

fun RBuilder.marketplaceDemands(props: MarketplaceDemandProps = MarketplaceDemandProps()) =
    child(MarketplaceDemand::class.rClass, props = props) {}

class MarketplaceDemand: RComponent<MarketplaceDemandProps, MarketplaceDemandState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
//                borderColor = Color.red
//                borderStyle = BorderStyle.solid
            }
            mGridList(cols = 1) {
                marketplaceDemand()
                marketplaceDemand()
                marketplaceDemand()
            }
        }
    }

}

