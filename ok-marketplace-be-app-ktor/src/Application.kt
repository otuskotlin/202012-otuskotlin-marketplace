package com.example

import com.example.controllers.DemandController
import com.example.controllers.ProposalController
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.features.*
import io.ktor.serialization.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val demandController = DemandController()
    val proposalController = ProposalController()

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = jsonConfig,
        )
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        route("/demand") {
            post("/read") {
                demandController.read(this)
            }
            post("/create") {
                demandController.create(this)
            }
            post("/update") {
                demandController.update(this)
            }
            post("/delete") {
                demandController.delete(this)
            }
            post("/list") {
                demandController.list(this)
            }
            post("/offers") {
                demandController.offers(this)
        }
        route("/proposal") {
            post("/read") {
                proposalController.read(this)
            }
            post("/create") {
                proposalController.create(this)
            }
            post("/update") {
                proposalController.update(this)
            }
            post("/delete") {
                proposalController.delete(this)
            }
            post("/list") {
                proposalController.list(this)
            }
            post("/offers") {
                proposalController.offers(this)
            }
        }
    }
}
}

