package ru.otus.otuskotlin.marketplace.business.logic

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext

class ProposalCrud {
    private val getPipeline = ProposalGetPipeline()
    private val createPipeline = ProposalCreatePipeline()
    private val updatePipeline = ProposalUpdatePipeline()
    private val deletePipeline = ProposalDeletePipeline()
    private val filterPipeline = ProposalFilterPipeline()
    private val offersPipeline = ProposalOffersPipeline()

    suspend fun get(context: MpBeContext) = getPipeline.run(context)
    suspend fun create(context: MpBeContext) = createPipeline.run(context)
    suspend fun update(context: MpBeContext) = updatePipeline.run(context)
    suspend fun delete(context: MpBeContext) = deletePipeline.run(context)
    suspend fun filter(context: MpBeContext) = filterPipeline.run(context)
    suspend fun offers(context: MpBeContext) = offersPipeline.run(context)
}