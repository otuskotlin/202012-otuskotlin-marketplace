package layouts.headed

import MarketplaceDsl
import com.ccfraser.muirwik.components.*
import layouts.header.marketplaceHeader
import kotlinx.css.*
import react.*
import styled.css

@MarketplaceDsl
class LayoutHeaded(props: ILayoutHeadedProps) : RComponent<ILayoutHeadedProps, LayoutHeadedState>(props) {
    override fun RBuilder.render() {
        val pageMainBody = props.pageMainBody ?: {}
        mGridContainer {
            attrs {
                spacing = MGridSpacing.spacing0
                alignItems = MGridAlignItems.stretch
                justify = MGridJustify.flexStart
                direction = MGridDirection.column
            }
            mGridItem(xs = MGridSize.cells12) {
                marketplaceHeader()
            }
            mGridItem {
                css {
                    margin(all = 0.px)
                    padding(all = 0.px)
                    width = 100.vw
                    overflow = Overflow.hidden
                }
                pageMainBody()
            }
        }
    }
}

fun RBuilder.layoutHeaded(block: LayoutHeadedConf.() -> Unit) {
    val conf = LayoutHeadedConf().apply(block)
    child(LayoutHeaded::class) {
        attrs.pageMainBody = conf.pageMainBody
    }
}
