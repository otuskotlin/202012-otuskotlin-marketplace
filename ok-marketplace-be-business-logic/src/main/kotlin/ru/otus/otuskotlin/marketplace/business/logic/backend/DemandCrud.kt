package ru.otus.otuskotlin.marketplace.business.logic.backend

import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.DemandCreate
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.DemandDelete
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.DemandFilter
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.DemandOffers
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.DemandRead
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.DemandUpdate
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IDemandRepository
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IProposalRepository

class DemandCrud(
    private val demandRepoTest: IDemandRepository = IDemandRepository.NONE,
    private val proposalRepoTest: IProposalRepository = IProposalRepository.NONE,
    private val demandRepoProd: IDemandRepository = IDemandRepository.NONE,
    private val proposalRepoProd: IProposalRepository = IProposalRepository.NONE,
) {
    suspend fun list(context: MpBeContext) {
        DemandFilter.execute(context.apply(this::configureContext))
    }

    suspend fun create(context: MpBeContext) {
        DemandCreate.execute(context.apply(this::configureContext))
    }

    suspend fun read(context: MpBeContext) {
        DemandRead.execute(context.apply(this::configureContext))
    }

    suspend fun update(context: MpBeContext) {
        DemandUpdate.execute(context.apply(this::configureContext))
    }

    suspend fun delete(context: MpBeContext) {
        DemandDelete.execute(context.apply(this::configureContext))
    }

    suspend fun offers(context: MpBeContext) {
        DemandOffers.execute(context.apply(this::configureContext))
    }

    private fun configureContext(context: MpBeContext) {
        context.demandRepoTest = demandRepoTest
        context.proposalRepoTest = proposalRepoTest
        context.demandRepoProd = demandRepoProd
        context.proposalRepoProd = proposalRepoProd
    }
}
