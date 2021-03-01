package com.example.controllers

import com.example.service.ProposalController
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import java.time.Instant

class DemandController {

    private val log = LoggerFactory.getLogger(this::class.java)!!

    suspend fun read(pipeline: PipelineContext<Unit, ApplicationCall>) {
        try {
            val query = pipeline.call.receive<MpMessage>() as MpRequestDemandRead

            // some logic

            val response: MpMessage = MpResponseDemandRead(
                responseId = "123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                demand = mockRead(query.demandId?: ""),
            )
            pipeline.call.respond(response)
        } catch (e: Throwable) {
            log.error("Read chain error", e)
        }
    }

    suspend fun create(pipeline: PipelineContext<Unit, ApplicationCall>) = try {
        val query = pipeline.call.receive<MpMessage>() as MpRequestDemandCreate

        // some logic

        val response: MpMessage = MpResponseDemandCreate(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            demand = mockUpdate(
                id = "d-123",
                avatar = query.createData?.avatar,
                title = query.createData?.title,
                description = query.createData?.description
            )
        )
        pipeline.call.respond(response)
    } catch (e: Throwable) {
        log.error("Create chain error", e)
    }

    suspend fun update(pipeline: PipelineContext<Unit, ApplicationCall>) = try {
        val query = pipeline.call.receive<MpMessage>() as MpRequestDemandUpdate
        val id = query.updateData?.id

        // some logic

        val response: MpMessage = if (id != null)
            MpResponseDemandUpdate(
                responseId = "123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                demand = mockUpdate(
                    id = id,
                    avatar = query.updateData?.avatar,
                    title = query.updateData?.title,
                    description = query.updateData?.description
                )
            )
        else
            MpResponseDemandUpdate(
                responseId = "123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                errors = listOf(
                    ErrorDto(
                        code = "wrong-id",
                        group = "validation",
                        field = "id",
                        level = ErrorDto.Level.ERROR,
                        message = "id of the demand to be updated cannot be empty"
                    )
                )
            )
        pipeline.call.respond(response)
    } catch (e: Throwable) {
        log.error("Update chain error", e)
    }

    suspend fun delete(pipeline: PipelineContext<Unit, ApplicationCall>) = try {
        val query = pipeline.call.receive<MpMessage>() as MpRequestDemandDelete

        // some logic

        val response: MpMessage = MpResponseDemandDelete(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            demand = mockRead(query.demandId ?: ""),
            deleted = true
        )
        pipeline.call.respond(response)
    } catch (e: Throwable) {
        log.error("Delete chain error", e)
    }

    suspend fun list(pipeline: PipelineContext<Unit, ApplicationCall>) = try {
        val query = pipeline.call.receive<MpMessage>() as MpRequestDemandList

        // some logic

        val response: MpMessage = MpResponseDemandList(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            demands = listOf(
                mockRead("d-001"),
                mockRead("d-002"),
                mockRead("d-003"),
                mockRead("d-004"),
                mockRead("d-005"),
                mockRead("d-006"),
            )
        )
        pipeline.call.respond(response)
    } catch (e: Throwable) {
        log.error("List chain error", e)
    }

    suspend fun offers(pipeline: PipelineContext<Unit, ApplicationCall>) = try {
        val query = pipeline.call.receive<MpMessage>() as MpRequestDemandOffersList

        // some logic

        val response: MpMessage = MpResponseDemandOffersList(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            demandProposals = listOf(
                ProposalController.mockRead("p-001"),
                ProposalController.mockRead("p-002"),
                ProposalController.mockRead("p-003"),
                ProposalController.mockRead("p-004"),
            )
        )
        pipeline.call.respond(response)
    } catch (e: Throwable) {
        log.error("Offers chain error", e)
    }

    companion object {
        fun mockUpdate(
            id: String,
            avatar: String?,
            title: String?,
            description: String?,
        ) = MpDemandDto(
            id = id,
            avatar = avatar,
            title = title,
            description = description,
            tagIds = setOf("t-001", "t-002"),
            techDets = setOf(
                TechDetsDto(
                    id = "td-001",
                    param = TechParamDto(
                        id = "tp-001",
                        name = "Tech Param 001",
                        description = "Tech Param Description 001",
                        priority = 0.0,
                        units = setOf(UnitTypeController.mockRead("ut-001", "kg")),
                    )
                )
            )
        )

        fun mockRead(id: String) = mockUpdate(
            id = id,
            avatar = "icon://menu",
            title = "Demand $id",
            description = "Description of demand $id"
        )
    }
}
