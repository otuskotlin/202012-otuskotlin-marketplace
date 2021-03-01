package layouts.columned

import MarketplaceDsl
import com.ccfraser.muirwik.components.*
import layouts.headed.layoutHeaded
import react.*

@MarketplaceDsl
class LayoutColumned(props: ILayoutColumnedProps) : RComponent<ILayoutColumnedProps, LayoutColumnedState>(props) {
    override fun RBuilder.render() {
        layoutHeaded {
            pageMainBody {
                mGridContainer {
                    attrs {
                        alignItems = MGridAlignItems.stretch
                        spacing = MGridSpacing.spacing0
                    }
                    mGridItem() {
                        attrs {
                            xs = MGridSize.cells12
                            sm = MGridSize.cells6
                            md = MGridSize.cells6
                            lg = MGridSize.cells6
                        }

                        this@LayoutColumned.props.blockDemands?.invoke(this)
                    }

                    mGridItem() {
                        attrs {
                            xs = MGridSize.cells12
                            sm = MGridSize.cells6
                            md = MGridSize.cells6
                            lg = MGridSize.cells6
                        }

                        this@LayoutColumned.props.blockProposals?.invoke(this)
                    }
                }
            }
        }
    }

}

fun RBuilder.layoutColumned(block: LayoutColumnedConf.() -> Unit): ReactElement {
    val conf = LayoutColumnedConf().apply(block)
    return child(LayoutColumned::class) {
        attrs.blockDemands = conf.demands ?: {}
        attrs.blockProposals = conf.proposals ?: {}
    }
}
