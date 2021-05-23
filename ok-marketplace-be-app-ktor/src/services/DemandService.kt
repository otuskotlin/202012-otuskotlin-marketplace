package ru.otus.otuskotlin.marketplace.backend.app.ktor.services

import org.slf4j.event.Level
import ru.otus.otuskotlin.marketplace.backend.logging.mpLogger
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

class DemandService(private val crud: DemandCrud) {

    private val logger = mpLogger(this::class.java)

    suspend fun list(context: MpBeContext, query: MpRequestDemandList?): MpResponseDemandList = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("demand-list-request-got")
        )
        crud.list(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("demand-list-request-handled")
        )
        return respondDemandList()
    }

    suspend fun create(context: MpBeContext, query: MpRequestDemandCreate?): MpResponseDemandCreate = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("demand-create-request-got")
        )
        crud.create(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("demand-create-request-handled")
        )
        return respondDemandCreate()
    }

    suspend fun read(context: MpBeContext, query: MpRequestDemandRead?): MpResponseDemandRead = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("demand-read-request-got")
        )
        crud.read(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("demand-read-request-handled")
        )
        return respondDemandRead()
    }

    suspend fun update(context: MpBeContext, query: MpRequestDemandUpdate?): MpResponseDemandUpdate = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("demand-update-request-got")
        )
        crud.update(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("demand-update-request-handled")
        )
        return respondDemandUpdate()
    }

    suspend fun delete(context: MpBeContext, query: MpRequestDemandDelete?): MpResponseDemandDelete = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("demand-delete-request-got")
        )
        crud.delete(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("demand-delete-request-handled")
        )
        return respondDemandDelete()
    }

    suspend fun offers(context: MpBeContext, query: MpRequestDemandOffers?): MpResponseDemandOffers = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("demand-offers-request-got")
        )
        crud.offers(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("demand-offers-request-handled")
        )
        return respondDemandOffers()
    }

}
