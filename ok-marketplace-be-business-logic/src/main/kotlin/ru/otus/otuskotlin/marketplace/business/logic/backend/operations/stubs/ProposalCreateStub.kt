package ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpStubCase
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.operation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline

object ProposalCreateStub : IOperation<MpBeContext> by pipeline({
    startIf { stubCase != MpStubCase.NONE }

    operation {
        startIf { stubCase == MpStubCase.PROPOSAL_CREATE_SUCCESS }
        execute {
            responseProposal = MpProposalModel(
                id = MpProposalIdModel("test-id"),
                avatar = requestProposal.avatar,
                title = requestProposal.title,
                description = requestProposal.description,
                tagIds = requestProposal.tagIds,
                techDets = requestProposal.techDets
            )
            status = MpBeContextStatus.FINISHING
        }
    }
})
