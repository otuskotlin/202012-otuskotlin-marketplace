package pages.proposals.view

import MarketplaceDsl
import backendService
import items.demands.MarketplaceDemandRouteParams
import items.demands.view.MarketplaceDemandViewProps
import items.demands.view.marketplaceDemandView
import items.proposals.MarketplaceProposalRouteParams
import items.proposals.view.MarketplaceProposalViewProps
import items.proposals.view.marketplaceProposalView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import layouts.headed.layoutHeaded
import models.ProposalModel
import pages.demands.view.PageDemandViewState
import pages.proposals.PageProposalUpdate
import react.*
import react.router.dom.RouteResultProps

@MarketplaceDsl
class PageProposalView(props: RouteResultProps<MarketplaceProposalRouteParams>) :
    RComponent<RouteResultProps<MarketplaceProposalRouteParams>, PageProposalViewState>(props) {

    override fun PageProposalViewState.init(props: RouteResultProps<MarketplaceProposalRouteParams>) {
        GlobalScope.launch {
            val proposal = backendService.proposal(props.match.params.proposalId)
            setState {
                this.proposal = proposal
                console.log("set state", proposal)
            }
        }
    }

    override fun RBuilder.render() {
        layoutHeaded {
            pageMainBody {
                val proposal = this@PageProposalView.state.proposal
                console.log("render", proposal)
                if (proposal != null) {
                    val props = MarketplaceProposalViewProps(proposal = proposal)
                    console.log("PageDemandView renders marketplaceDemandView", props)
                    marketplaceProposalView(props = props)
                } else {
                    console.log("PageDemandView skips marketplaceDemandView")
                }
            }
        }
    }
}

fun RBuilder.pageProposalView(props: RouteResultProps<MarketplaceProposalRouteParams>) =
    child(PageProposalView::class.rClass, props = props) { }
