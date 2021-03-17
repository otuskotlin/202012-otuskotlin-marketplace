package ru.otus.otuskotlin.marketplace.business.logic.backend.operations

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.operation

object InitializePipeline : IOperation<MpBeContext> by operation({
    startIf { status == MpBeContextStatus.NONE }
    execute { status = MpBeContextStatus.RUNNING }
})