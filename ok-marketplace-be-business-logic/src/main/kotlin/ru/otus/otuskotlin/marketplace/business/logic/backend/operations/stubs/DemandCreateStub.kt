package ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpStubCase
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.operation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline

object DemandCreateStub : IOperation<MpBeContext> by pipeline({
    startIf { stubCase != MpStubCase.NONE }

    operation {
        startIf { stubCase == MpStubCase.DEMAND_CREATE_SUCCESS }
        execute {
            responseDemand = MpDemandModel(
                id = MpDemandIdModel("test-id"),
                avatar = requestDemand.avatar,
                title = requestDemand.title,
                description = requestDemand.description,
                tagIds = requestDemand.tagIds,
                techDets = requestDemand.techDets
            )
            status = MpBeContextStatus.FINISHING
        }
    }
})
