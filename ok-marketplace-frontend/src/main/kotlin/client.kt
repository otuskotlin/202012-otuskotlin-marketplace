import items.demands.MarketplaceDemandRouteParams
import items.demands.update.MarketplaceDemandUpdate
import items.demands.view.MarketplaceDemandView
import items.proposals.MarketplaceProposalRouteParams
import items.proposals.update.MarketplaceProposalUpdate
import items.proposals.view.MarketplaceProposalView
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
                    route<MarketplaceDemandRouteParams>(MarketplaceDemandView.linkMask, exact = true) {
                        pageDemandView(it)
                    }
                    route<MarketplaceDemandRouteParams>(MarketplaceDemandUpdate.linkMask, exact = true) {
                        pageDemandUpdate(it)
                    }
                    route<MarketplaceProposalRouteParams>(MarketplaceProposalView.linkMask, exact = true) {
                        pageProposalView(it)
                    }
                    route<MarketplaceProposalRouteParams>(MarketplaceProposalUpdate.linkMask, exact = true) {
                        pageProposalUpdate(it)
                    }
                }
            }
        }
    }
}

