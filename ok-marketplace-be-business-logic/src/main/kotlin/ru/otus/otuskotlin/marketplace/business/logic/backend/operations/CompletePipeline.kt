package ru.otus.otuskotlin.marketplace.business.logic.backend.operations

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.operation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline

object CompletePipeline : IOperation<MpBeContext> by pipeline({
    operation {
        startIf { status in setOf(MpBeContextStatus.RUNNING, MpBeContextStatus.FINISHING) }
        execute { status = MpBeContextStatus.SUCCESS }
    }
    operation {
        startIf { status != MpBeContextStatus.SUCCESS }
        execute { status = MpBeContextStatus.ERROR }
    }
})