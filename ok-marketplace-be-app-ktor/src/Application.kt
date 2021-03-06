package com.example

import com.example.service.DemandService
import com.example.service.ProposalService
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
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val demandService = DemandService()
    val proposalService = ProposalService()

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
                    call.respond(demandService.get(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseDemandRead(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/create") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandCreate
                    call.respond(demandService.create(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseDemandCreate(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/update") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandUpdate
                    call.respond(demandService.update(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseDemandUpdate(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/delete") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandDelete
                    call.respond(demandService.delete(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseDemandDelete(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/filter") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandList
                    call.respond(demandService.filter(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseDemandList(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/offers") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestDemandOffersList
                    call.respond(demandService.offers(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseDemandOffersList(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
        }
        route("/proposals") {
            post("/get") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalRead
                    call.respond(proposalService.get(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseProposalRead(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/create") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalCreate
                    call.respond(proposalService.create(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseProposalCreate(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/update") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalUpdate
                    call.respond(proposalService.update(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseProposalUpdate(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/delete") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalDelete
                    call.respond(proposalService.delete(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseProposalDelete(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/filter") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalList
                    call.respond(proposalService.filter(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseProposalList(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/offers") {
                try {
                    val query = call.receive<MpMessage>() as MpRequestProposalOffersList
                    call.respond(proposalService.offers(query))
                } catch(e: Throwable) {
                    call.respond(
                        MpResponseProposalOffersList(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
        }
    }
}

