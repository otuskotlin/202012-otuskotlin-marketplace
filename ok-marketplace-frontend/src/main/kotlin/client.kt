import items.demands.MarketplaceDemandRouteParams
import items.proposals.MarketplaceProposalRouteParams
import kotlinx.browser.document
import kotlinx.browser.window
import pages.demands.pageDemandUpdate
import pages.demands.pageDemandView
import pages.home.PageHome
import pages.proposals.pageProposalUpdate
import pages.proposals.pageProposalView
import react.dom.render
import react.router.dom.hashRouter
import react.router.dom.route
import react.router.dom.switch

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            hashRouter {
                switch {
                    route("/", exact = true, component = PageHome::class)
                    route<MarketplaceDemandRouteParams>("/demand/:demandId", exact = true) {
                        pageDemandView(it)
                    }
                    route<MarketplaceDemandRouteParams>("/demand/:demandId/edit", exact = true) {
                        pageDemandUpdate(it)
                    }
                    route<MarketplaceProposalRouteParams>("/proposal/:proposalId", exact = true) {
                        pageProposalView(it)
                    }
                    route<MarketplaceProposalRouteParams>("/proposal/:proposalId/edit", exact = true) {
                        pageProposalUpdate(it)
                    }
                }
            }
        }
    }
}

