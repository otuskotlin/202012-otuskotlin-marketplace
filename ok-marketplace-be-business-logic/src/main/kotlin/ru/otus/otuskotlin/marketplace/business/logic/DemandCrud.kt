package ru.otus.otuskotlin.marketplace.business.logic

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext

class DemandCrud {
    private val getPipeline = DemandGetPipeline()
    private val createPipeline = DemandCreatePipeline()
    private val updatePipeline = DemandUpdatePipeline()
    private val deletePipeline = DemandDeletePipeline()
    private val filterPipeline = DemandFilterPipeline()
    private val offersPipeline = DemandOffersPipeline()

    suspend fun get(context: MpBeContext) = getPipeline.run(context)
    suspend fun create(context: MpBeContext) = createPipeline.run(context)
    suspend fun update(context: MpBeContext) = updatePipeline.run(context)
    suspend fun delete(context: MpBeContext) = deletePipeline.run(context)
    suspend fun filter(context: MpBeContext) = filterPipeline.run(context)
    suspend fun offers(context: MpBeContext) = offersPipeline.run(context)
}