package pages.demands

import MarketplaceDsl
import items.demands.MarketplaceDemandRouteParams
import items.demands.view.MarketplaceDemandViewProps
import items.demands.view.marketplaceDemandView
import layouts.headed.layoutHeaded
import react.RBuilder
import react.RComponent
import react.RState
import react.rClass
import react.router.dom.RouteResultProps

@MarketplaceDsl
class PageDemandView(props: MarketplaceDemandViewProps) : RComponent<MarketplaceDemandViewProps, RState>(props) {
    override fun RBuilder.render() {
        layoutHeaded {
            pageMainBody {
                marketplaceDemandView(this@PageDemandView.props)
            }
        }
    }
}

fun RBuilder.pageDemandView(props: RouteResultProps<MarketplaceDemandRouteParams>) =
    child(PageDemandView::class.rClass, props = props) { }
