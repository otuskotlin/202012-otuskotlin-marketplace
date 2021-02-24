package com.example

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.features.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
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

        route("/demands") {
            post("/get") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandRead
                } catch(e: Throwable) {

                }
            }
            post("/create") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandCreate
                } catch(e: Throwable) {

                }
            }
            post("/update") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandUpdate
                } catch(e: Throwable) {

                }
            }
            post("/delete") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandDelete
                } catch(e: Throwable) {

                }
            }
            post("/filter") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandList
                } catch(e: Throwable) {

                }
            }
            post("/offers") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandOffersList
                } catch(e: Throwable) {

                }
            }
        }
        route("/proposals") {
            post("/get") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalRead
                } catch(e: Throwable) {

                }
            }
            post("/create") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalCreate
                } catch(e: Throwable) {

                }
            }
            post("/update") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalUpdate
                } catch(e: Throwable) {

                }
            }
            post("/delete") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalDelete
                } catch(e: Throwable) {

                }
            }
            post("/filter") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalList
                } catch(e: Throwable) {

                }
            }
            post("/offers") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalOffersList
                } catch(e: Throwable) {

                }
            }
        }
    }
}

