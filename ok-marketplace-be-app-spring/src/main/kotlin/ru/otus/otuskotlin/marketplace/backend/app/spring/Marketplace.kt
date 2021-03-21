package ru.otus.otuskotlin.marketplace.backend.app.spring

import org.springframework.fu.kofu.webApplication
import org.springframework.fu.kofu.webmvc.webMvc
import ru.otus.otuskotlin.marketplace.backend.app.spring.controllers.DemandController
import ru.otus.otuskotlin.marketplace.backend.app.spring.controllers.ProposalController
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints


val app = webApplication {
    beans {
        bean<DemandCrud>()
        bean<ProposalCrud>()

        bean<DemandController>()
        bean<ProposalController>()
    }
    webMvc {
        port = if (profiles.contains("test")) 8181 else 8080
        router {
            val demandService = ref<DemandController>()
            POST(RestEndpoints.demandList, demandService::list)
            POST(RestEndpoints.demandCreate, demandService::create)
            POST(RestEndpoints.demandRead, demandService::read)
            POST(RestEndpoints.demandUpdate, demandService::update)
            POST(RestEndpoints.demandDelete, demandService::delete)
            POST(RestEndpoints.demandOffers, demandService::offers)

            val proposalService = ref<ProposalController>()
            POST(RestEndpoints.proposalList, proposalService::list)
            POST(RestEndpoints.proposalCreate, proposalService::create)
            POST(RestEndpoints.proposalRead, proposalService::read)
            POST(RestEndpoints.proposalUpdate, proposalService::update)
            POST(RestEndpoints.proposalDelete, proposalService::delete)
            POST(RestEndpoints.proposalOffers, proposalService::offers)
        }
        converters {
            string()
            kotlinSerialization()
        }
    }
}

fun main() {
    app.run()
}
