package ru.otus.otuskotlin.marketplace.backend.app.spring

import org.springframework.fu.kofu.webApplication
import org.springframework.fu.kofu.webmvc.webMvc
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.otus.otuskotlin.marketplace.backend.app.spring.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.app.spring.services.ProposalService


val app = webApplication {
    beans {
        bean<DemandService>()
        bean<ProposalService>()
    }
    webMvc {
        port = if (profiles.contains("test")) 8181 else 8080
        router {
            val demandService = ref<DemandService>()
            POST("/demand/list", demandService::list)
            POST("/demand/create", demandService::create)
            POST("/demand/read", demandService::read)
            POST("/demand/update", demandService::update)
            POST("/demand/delete", demandService::delete)
            POST("/demand/offers", demandService::offers)

            val proposalService = ref<ProposalService>()
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

data class Sample(val message: String)

class SampleService {
    fun generateMessage() = "Hello world!"
}

@Suppress("UNUSED_PARAMETER")
class SampleHandler(private val sampleService: SampleService) {
    fun hello(request: ServerRequest)= ok().body(sampleService.generateMessage())
    fun json(request: ServerRequest) = ok().body(Sample(sampleService.generateMessage()))
}
