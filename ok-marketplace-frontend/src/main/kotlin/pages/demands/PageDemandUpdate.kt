package pages.demands

import MarketplaceDsl
import items.demands.MarketplaceDemandRouteParams
import items.demands.update.MarketplaceDemandUpdateProps
import items.demands.update.marketplaceDemandUpdate
import layouts.headed.layoutHeaded
import react.RBuilder
import react.RComponent
import react.RState
import react.rClass
import react.router.dom.RouteResultProps

@MarketplaceDsl
class PageDemandUpdate(props: MarketplaceDemandUpdateProps) : RComponent<MarketplaceDemandUpdateProps, RState>(props) {
    override fun RBuilder.render() {
        layoutHeaded {
            pageMainBody {
                marketplaceDemandUpdate(this@PageDemandUpdate.props)
            }
        }
    }
}

fun RBuilder.pageDemandUpdate(props: RouteResultProps<MarketplaceDemandRouteParams>) =
    child(PageDemandUpdate::class.rClass, props = props) { }
