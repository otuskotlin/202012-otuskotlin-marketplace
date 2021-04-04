package ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpStubCase
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.operation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline

object DemandReadStub : IOperation<MpBeContext> by pipeline({
    startIf { stubCase != MpStubCase.NONE }

    operation {
        startIf { stubCase == MpStubCase.DEMAND_READ_SUCCESS }
        execute {
            responseDemand = MpDemandModel(
                id = requestDemandId,
                avatar = "icon://menu",
                title = "Demand ${requestDemandId.asString()}",
                description = "Description of demand ${requestDemandId.asString()}",
                tagIds = mutableSetOf("1", "2", "3")
            )
            status = MpBeContextStatus.FINISHING
        }
    }
})
