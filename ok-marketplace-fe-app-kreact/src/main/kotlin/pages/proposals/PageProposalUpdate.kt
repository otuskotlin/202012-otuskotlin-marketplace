package pages.proposals

import MarketplaceDsl
import items.proposals.MarketplaceProposalRouteParams
import items.proposals.update.MarketplaceProposalUpdateProps
import items.proposals.update.marketplaceProposalUpdate
import layouts.headed.layoutHeaded
import react.RBuilder
import react.RComponent
import react.RState
import react.rClass
import react.router.dom.RouteResultProps

@MarketplaceDsl
class PageProposalUpdate(props: MarketplaceProposalUpdateProps) : RComponent<MarketplaceProposalUpdateProps, RState>(props) {
    override fun RBuilder.render() {
        layoutHeaded {
            pageMainBody {
                marketplaceProposalUpdate(this@PageProposalUpdate.props)
            }
        }
    }
}

fun RBuilder.pageProposalUpdate(props: RouteResultProps<MarketplaceProposalRouteParams>) =
    child(PageProposalUpdate::class.rClass, props = props) { }
