package ru.otus.otuskotlin.marketplace.backend.app.spring

import org.springframework.fu.kofu.webApplication
import org.springframework.fu.kofu.webmvc.webMvc
import ru.otus.otuskotlin.marketplace.backend.app.spring.controllers.DemandController
import ru.otus.otuskotlin.marketplace.backend.app.spring.controllers.ProposalController


private val app = webApplication {
    beans {
        bean<DemandController>()
        bean<ProposalController>()
    }
    webMvc {
        port = if (profiles.contains("test")) 8181 else 8080
        router {
            val demandService = ref<DemandController>()
            POST("/demand/list", demandService::list)
            POST("/demand/create", demandService::create)
            POST("/demand/read", demandService::read)
            POST("/demand/update", demandService::update)
            POST("/demand/delete", demandService::delete)
            POST("/demand/offers", demandService::offers)

            val proposalService = ref<ProposalController>()
            POST("/proposal/list", proposalService::list)
            POST("/proposal/create", proposalService::create)
            POST("/proposal/read", proposalService::read)
            POST("/proposal/update", proposalService::update)
            POST("/proposal/delete", proposalService::delete)
            POST("/proposal/offers", proposalService::offers)
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
