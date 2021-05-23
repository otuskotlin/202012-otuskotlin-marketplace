package ru.otus.otuskotlin.marketplace.backend.app.ktor.services

import org.slf4j.event.Level
import ru.otus.otuskotlin.marketplace.backend.logging.mpLogger
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

class ProposalService(private val crud: ProposalCrud) {

    private val logger = mpLogger(this::class.java)

    suspend fun list(context: MpBeContext, query: MpRequestProposalList?): MpResponseProposalList = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("proposal-list-request-got")
        )
        crud.list(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("proposal-list-request-handled")
        )
        return respondProposalList()
    }

    suspend fun create(context: MpBeContext, query: MpRequestProposalCreate?): MpResponseProposalCreate = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("proposal-create-request-got")
        )
        crud.create(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("proposal-create-request-handled")
        )
        return respondProposalCreate()
    }

    suspend fun read(context: MpBeContext, query: MpRequestProposalRead?): MpResponseProposalRead = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("proposal-read-request-got")
        )
        crud.read(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("proposal-read-request-handled")
        )
        return respondProposalRead()
    }

    suspend fun update(context: MpBeContext, query: MpRequestProposalUpdate?): MpResponseProposalUpdate = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("proposal-update-request-got")
        )
        crud.update(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("proposal-update-request-handled")
        )
        return respondProposalUpdate()
    }

    suspend fun delete(context: MpBeContext, query: MpRequestProposalDelete?): MpResponseProposalDelete = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("proposal-delete-request-got")
        )
        crud.delete(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("proposal-delete-request-handled")
        )
        return respondProposalDelete()
    }

    suspend fun offers(context: MpBeContext, query: MpRequestProposalOffers?): MpResponseProposalOffers = with (context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("proposal-offers-request-got")
        )
        crud.offers(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("proposal-offers-request-handled")
        )
        return respondProposalOffers()
    }

}
