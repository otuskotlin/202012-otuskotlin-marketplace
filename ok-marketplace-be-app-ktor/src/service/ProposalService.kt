package com.example.service

import ru.otus.otuskotlin.marketplace.backend.mappers.*
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*


class ProposalService {

    private val proposal = MpProposalModel(
        id = MpProposalIdModel("test-id"),
        avatar = "test-avatar",
        title = "test-proposal",
        description = "test-description",
        tagIds = mutableSetOf("1", "2", "3"),
    )

    suspend fun get(query: MpRequestProposalRead) = MpBeContext().run {
        try {
            setQuery(query)
            responseProposal = proposal
            respondProposalGet().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseProposalRead(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun create(query: MpRequestProposalCreate) = MpBeContext().run {
        try {
            setQuery(query)
            responseProposal = proposal
            respondProposalCreate().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseProposalCreate(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun update(query: MpRequestProposalUpdate) = MpBeContext().run {
        try {
            setQuery(query)
            responseProposal = proposal
            respondProposalUpdate().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseProposalUpdate(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun delete(query: MpRequestProposalDelete)  = MpBeContext().run {
        try {
            setQuery(query)
            responseProposal = proposal
            respondProposalDelete().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseProposalDelete(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun filter(query: MpRequestProposalList) = MpBeContext().run {
        try {
            setQuery(query)
            responseProposals = mutableListOf(proposal)
            respondProposalList().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseProposalList(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun offers(query: MpRequestProposalOffersList) = MpBeContext().run {
        try {
            setQuery(query)
            responseDemands = mutableListOf(
                MpDemandModel(
                    id = MpDemandIdModel("test-id"),
                    avatar = "test-avatar",
                    title = "test-demand",
                    description = "test-description",
                    tagIds = mutableSetOf("1", "2", "3"),
                )
            )
            respondProposalOffers().copy(
                responseId = "123",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            MpResponseProposalOffersList(
                responseId = "123",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

}
