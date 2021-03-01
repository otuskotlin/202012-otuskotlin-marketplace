package ru.otus.otuskotlin.marketplace.backend.app.spring.controllers

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechParamDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import java.time.Instant

class DemandController {
    fun list(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestDemandList::class.java)
        val response = MpResponseDemandList(
            demands = listOf(
                mockRead("d-001"),
                mockRead("d-002"),
                mockRead("d-003"),
                mockRead("d-004"),
                mockRead("d-005"),
                mockRead("d-006"),
            )
        )
        return ok().body(response)
    }

    fun create(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestDemandCreate::class.java)
        val response = MpResponseDemandCreate(
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
        return ok().body(response)
    }

    fun read(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestDemandRead::class.java)
        val response = MpResponseDemandRead(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            demand = mockRead(query.demandId ?: "")
        )
        return ok().body(response)
    }

    fun update(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestDemandUpdate::class.java)
        val id = query.updateData?.id
        val response = if (id != null)
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
        return ok().body(response)
    }

    fun delete(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestDemandDelete::class.java)
        return ok().body(
            MpResponseDemandDelete(
                responseId = "123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                demand = mockRead(query.demandId ?: ""),
                deleted = true
            )
        )
    }

    fun offers(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestDemandOffersList::class.java)
        return ok().body(
            MpResponseDemandOffersList(
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
        )
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
