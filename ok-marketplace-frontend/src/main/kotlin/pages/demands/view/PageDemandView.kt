package pages.demands.view

import MarketplaceDsl
import backendService
import items.demands.MarketplaceDemandRouteParams
import items.demands.view.MarketplaceDemandViewProps
import items.demands.view.marketplaceDemandView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import layouts.headed.layoutHeaded
import models.DemandModel
import react.*
import react.router.dom.RouteResultProps
import react.router.dom.withRouter

@MarketplaceDsl
class PageDemandView(props: RouteResultProps<MarketplaceDemandRouteParams>) :
    RComponent<RouteResultProps<MarketplaceDemandRouteParams>, PageDemandViewState>(props) {

    override fun PageDemandViewState.init(props: RouteResultProps<MarketplaceDemandRouteParams>) {
        GlobalScope.launch {
            val demand = backendService.demand(props.match.params.demandId)
            setState {
                this.demand = demand
                console.log("set state", demand)
            }
        }
    }

    override fun RBuilder.render() {
        layoutHeaded {
            pageMainBody {
                val demand = this@PageDemandView.state.demand
                console.log("render", demand)
                if (demand != null) {
                    val props = MarketplaceDemandViewProps(demand = demand)
                    console.log("PageDemandView renders marketplaceDemandView", props)
                    marketplaceDemandView(props = props)
                } else {
                    console.log("PageDemandView skips marketplaceDemandView")
                }
            }
        }
    }
}

fun RBuilder.pageDemandView(props: RouteResultProps<MarketplaceDemandRouteParams>): ReactElement =
    withRouter(PageDemandView::class).invoke {
        child(PageDemandView::class.rClass, props = props) { }
    }
