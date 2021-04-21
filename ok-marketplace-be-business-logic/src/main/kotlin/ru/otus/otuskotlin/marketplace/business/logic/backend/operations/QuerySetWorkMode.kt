package ru.otus.otuskotlin.marketplace.business.logic.backend.operations

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpWorkMode
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.operation

object QuerySetWorkMode: IOperation<MpBeContext> by operation ({
    startIf { status == MpBeContextStatus.RUNNING }
    execute {
        demandRepo = when(workMode) {
            MpWorkMode.TEST -> demandRepoTest
            MpWorkMode.PROD -> demandRepoProd
        }
        proposalRepo = when(workMode) {
            MpWorkMode.TEST -> proposalRepoTest
            MpWorkMode.PROD -> proposalRepoProd
        }
    }
})
