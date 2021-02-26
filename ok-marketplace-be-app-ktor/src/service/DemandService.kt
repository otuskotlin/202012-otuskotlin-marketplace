package com.example.service

import ru.otus.otuskotlin.marketplace.backend.mappers.*
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

class DemandService {
    private val demand = MpDemandModel(
        id = MpDemandIdModel("test-id"),
        avatar = "test-avatar",
        title = "test-demand",
        description = "test-description",
        tagIds = mutableSetOf("1", "2", "3"),
    )

    suspend fun get(query: MpRequestDemandRead) = MpBeContext().run {
        try {
            setQuery(query)
            responseDemand = demand
            respondDemandGet().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseDemandRead(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun create(query: MpRequestDemandCreate) = MpBeContext().run {
        try {
            setQuery(query)
            responseDemand = demand
            respondDemandCreate().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseDemandCreate(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun update(query: MpRequestDemandUpdate) = MpBeContext().run {
        try {
            setQuery(query)
            responseDemand = demand
            respondDemandUpdate().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseDemandUpdate(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun delete(query: MpRequestDemandDelete) = MpBeContext().run {
        try {
            setQuery(query)
            responseDemand = demand
            respondDemandDelete().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseDemandDelete(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun filter(query: MpRequestDemandList) = MpBeContext().run {
        try {
            setQuery(query)
            responseDemands = mutableListOf(demand)
            respondDemandList().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseDemandList(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun offers(query: MpRequestDemandOffersList) = MpBeContext().run {
        try {
            setQuery(query)
            responseProposals = mutableListOf(
                MpProposalModel(
                    id = MpProposalIdModel("test-id"),
                    avatar = "test-avatar",
                    title = "test-proposal",
                    description = "test-description",
                    tagIds = mutableSetOf("1", "2", "3"),
                )
            )
            respondDemandOffers().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseDemandOffersList(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }
}
