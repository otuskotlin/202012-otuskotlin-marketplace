package ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpStubCase
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.operation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline

object ProposalFIlterStub : IOperation<MpBeContext> by pipeline({
    startIf { stubCase != MpStubCase.NONE }

    operation {
        startIf { stubCase == MpStubCase.PROPOSAL_FILTER_SUCCESS }
        execute {
            responseProposals.add(
                MpProposalModel(
                    id = MpProposalIdModel("test-id"),
                    avatar = "test-avatar",
                    title = "test-proposal",
                    description = "test-description",
                    tagIds = mutableSetOf("1", "2", "3")
                )
            )
            status = MpBeContextStatus.FINISHING
        }
    }
})