package com.example.service

import com.example.controllers.DemandController
import com.example.controllers.UnitTypeController
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.marketplace.backend.mappers.*
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandCreate
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpResponseDemandCreate
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*
import java.time.Instant


class ProposalController {
    private val log = LoggerFactory.getLogger(this::class.java)!!

    suspend fun read(pipeline: PipelineContext<Unit, ApplicationCall>) = try {
        val query = pipeline.call.receive<MpMessage>() as MpRequestProposalRead

        // some logic

        val response: MpMessage = MpResponseProposalRead(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            proposal = mockRead(query.proposalId ?: "")
        )
        pipeline.call.respond(response)
    } catch (e: Throwable) {
        log.error("Read chain error", e)
    }

    suspend fun create(pipeline: PipelineContext<Unit, ApplicationCall>) = try {
        val query = pipeline.call.receive<MpMessage>() as MpRequestProposalCreate

        // some logic

        val response: MpMessage = MpResponseProposalCreate(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            proposal = mockUpdate(
                id = "p-123",
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
        val query = pipeline.call.receive<MpMessage>() as MpRequestProposalUpdate
        val id = query.updateData?.id

        // some logic

        val response = if (id != null)
            MpResponseProposalUpdate(
                responseId = "123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                proposal = mockUpdate(
                    id = id,
                    avatar = query.updateData?.avatar,
                    title = query.updateData?.title,
                    description = query.updateData?.description
                )
            )
        else
            MpResponseProposalUpdate(
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
                        message = "id of the proposal to be updated cannot be empty"
                    )
                )
            )
        pipeline.call.respond(response)
    } catch (e: Throwable) {
        log.error("Update chain error", e)
    }

    suspend fun delete(pipeline: PipelineContext<Unit, ApplicationCall>)  = try {
        val query = pipeline.call.receive<MpMessage>() as MpRequestProposalDelete

        // some logic

        val response: MpMessage = MpResponseProposalDelete(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            proposal = mockRead(query.proposalId ?: ""),
            deleted = true
        )
        pipeline.call.respond(response)
    } catch (e: Throwable) {
        log.error("Delete chain error", e)
    }

    suspend fun list(pipeline: PipelineContext<Unit, ApplicationCall>) = try {
        val query = pipeline.call.receive<MpMessage>() as MpRequestProposalList

        // some logic

        val response: MpMessage = MpResponseProposalList(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            proposals = listOf(
                mockRead("p-001"),
                mockRead("p-002"),
                mockRead("p-003"),
                mockRead("p-004"),
                mockRead("p-005"),
                mockRead("p-006"),
            )
        )
        pipeline.call.respond(response)
    } catch (e: Throwable) {
        log.error("List chain error", e)
    }

    suspend fun offers(pipeline: PipelineContext<Unit, ApplicationCall>) = try {
        val query = pipeline.call.receive<MpMessage>() as MpRequestProposalOffersList

        // some logic

        val response: MpMessage = MpResponseProposalOffersList(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            proposalDemands = listOf(
                DemandController.mockRead("d-001"),
                DemandController.mockRead("d-002"),
                DemandController.mockRead("d-003"),
                DemandController.mockRead("d-004"),
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
        ) = MpProposalDto(
            id = id,
            avatar = avatar,
            title = title,
            description = description,
            tagIds = setOf("t-001", "t-002"),
            techDets = setOf(
                TechDetsDto(
                    id = "tp-001",
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
            title = "Proposal $id",
            description = "Description of proposal $id"
        )
    }
}
