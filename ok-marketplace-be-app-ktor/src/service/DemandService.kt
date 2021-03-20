package ru.otus.otuskotlin.marketplace.backend.app.ktor.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.marketplace.backend.mappers.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpError
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

class DemandService(
    private val crud: DemandCrud
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    suspend fun read(
        query: MpRequestDemandRead = MpRequestDemandRead(),
        error: MpError? = null,
        status: MpBeContextStatus = MpBeContextStatus.RUNNING
    ): MpMessage = MpBeContext(
        errors = error?.let { mutableListOf(it) } ?: mutableListOf(),
        status = status
    ).run {
        try {
            crud.read(setQuery(query))
            respondDemandGet().copy(
                responseId = "123",
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            logger.error("Error handling ${this::class.java}::read business chain", e)
            withContext(Dispatchers.Unconfined) {
                crud.read(setException(e))
                respondDemandGet().copy(
                    responseId = "123",
                    onRequest = query.requestId
                )
            }
        }
    }

    suspend fun create(query: MpRequestDemandCreate): MpMessage = MpBeContext().run {
        try {
            crud.create(setQuery(query))
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

    suspend fun update(query: MpRequestDemandUpdate): MpMessage = MpBeContext().run {
        try {
            crud.update(setQuery(query))
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

    suspend fun delete(query: MpRequestDemandDelete): MpMessage = MpBeContext().run {
        try {
            crud.delete(setQuery(query))
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

    suspend fun filter(query: MpRequestDemandList): MpMessage = MpBeContext().run {
        try {
            crud.filter(setQuery(query))
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

    suspend fun offers(query: MpRequestDemandOffersList): MpMessage = MpBeContext().run {
        try {
            crud.offers(setQuery(query))
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
