package ru.otus.otuskotlin.marketplace.business.logic.backend

import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.ProposalCreate
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.ProposalDelete
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.ProposalFilter
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.ProposalOffers
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.ProposalRead
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.ProposalUpdate
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IProposalRepository

class ProposalCrud(
    private val proposalRepoTest: IProposalRepository = IProposalRepository.NONE
) {
    suspend fun list(context: MpBeContext) {
        ProposalFilter.execute(context.apply(this::configureContext))
    }

    suspend fun create(context: MpBeContext) {
        ProposalCreate.execute(context.apply(this::configureContext))
    }

    suspend fun read(context: MpBeContext) {
        ProposalRead.execute(context.apply(this::configureContext))
    }

    suspend fun update(context: MpBeContext) {
        ProposalUpdate.execute(context.apply(this::configureContext))
    }

    suspend fun delete(context: MpBeContext) {
        ProposalDelete.execute(context.apply(this::configureContext))
    }

    suspend fun offers(context: MpBeContext) {
        ProposalOffers.execute(context.apply(this::configureContext))
    }

    private fun configureContext(context: MpBeContext) {
        context.proposalRepoTest = proposalRepoTest
    }
}
