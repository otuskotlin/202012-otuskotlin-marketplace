package pages.proposals

import MarketplaceDsl
import items.proposals.MarketplaceProposalRouteParams
import items.proposals.view.MarketplaceProposalViewProps
import items.proposals.view.marketplaceProposalView
import layouts.headed.layoutHeaded
import react.RBuilder
import react.RComponent
import react.RState
import react.rClass
import react.router.dom.RouteResultProps

@MarketplaceDsl
class PageProposalView(props: MarketplaceProposalViewProps) : RComponent<MarketplaceProposalViewProps, RState>(props) {
    override fun RBuilder.render() {
        layoutHeaded {
            pageMainBody {
                marketplaceProposalView(this@PageProposalView.props)
            }
        }
    }
}

fun RBuilder.pageProposalView(props: RouteResultProps<MarketplaceProposalRouteParams>) =
    child(PageProposalUpdate::class.rClass, props = props) { }
