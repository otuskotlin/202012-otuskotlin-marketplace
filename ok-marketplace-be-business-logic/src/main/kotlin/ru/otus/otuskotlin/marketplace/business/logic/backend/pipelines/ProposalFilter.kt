package ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines

import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.CompletePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs.DemandCreateStub
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.InitializePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs.ProposalCreateStub
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs.ProposalFIlterStub
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline

object ProposalFilter : IOperation<MpBeContext> by pipeline({
    execute(InitializePipeline)

    execute(ProposalFIlterStub)

    execute(CompletePipeline)
})