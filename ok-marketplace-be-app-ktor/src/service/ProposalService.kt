package com.example.service

import ru.otus.otuskotlin.marketplace.backend.mappers.respondProposalCreate
import ru.otus.otuskotlin.marketplace.backend.mappers.respondProposalDelete
import ru.otus.otuskotlin.marketplace.backend.mappers.respondProposalGet
import ru.otus.otuskotlin.marketplace.backend.mappers.respondProposalList
import ru.otus.otuskotlin.marketplace.backend.mappers.respondProposalOffers
import ru.otus.otuskotlin.marketplace.backend.mappers.respondProposalUpdate
import ru.otus.otuskotlin.marketplace.backend.mappers.setQuery
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalCreate
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalDelete
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalOffersList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalRead
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalUpdate
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalCreate
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalDelete
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalOffersList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalRead
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalUpdate


class ProposalService(
    private val crud: ProposalCrud
) {
    suspend fun get(query: MpRequestProposalRead): MpMessage = MpBeContext().run {
        try {
            crud.read(setQuery(query))
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

    suspend fun create(query: MpRequestProposalCreate): MpMessage = MpBeContext().run {
        try {
            crud.create(setQuery(query))
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

    suspend fun update(query: MpRequestProposalUpdate): MpMessage = MpBeContext().run {
        try {
            crud.update(setQuery(query))
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

    suspend fun delete(query: MpRequestProposalDelete): MpMessage = MpBeContext().run {
        try {
            crud.delete(setQuery(query))
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

    suspend fun filter(query: MpRequestProposalList): MpMessage = MpBeContext().run {
        try {
            crud.filter(setQuery(query))
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

    suspend fun offers(query: MpRequestProposalOffersList): MpMessage = MpBeContext().run {
        try {
            crud.offers(setQuery(query))
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
