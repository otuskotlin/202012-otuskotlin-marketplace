import items.demands.MarketplaceDemandRouteParams
import items.proposals.MarketplaceProposalRouteParams
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.*
import pages.demands.pageDemandUpdate
import pages.demands.view.pageDemandView
import pages.home.PageHome
import pages.proposals.pageProposalUpdate
import pages.proposals.view.pageProposalView
import react.dom.render
import react.router.dom.*
import services.MarketplaceBackendService

val backendService = MarketplaceBackendService()
val mainScope = MainScope() + Job()

suspend fun main() {
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
                        console.log("proposal!!!!!!!!!!", it)
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

