package ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines

import ru.otus.otuskotlin.marketplace.business.logic.backend.helpers.validation
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.CompletePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs.DemandCreateStub
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.InitializePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs.ProposalCreateStub
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs.ProposalOffersStub
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.kmp.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline

object ProposalOffers : IOperation<MpBeContext> by pipeline({
    execute(InitializePipeline)

    execute(ProposalOffersStub)

    validation {
        validate<String?> {
            on { requestProposalId.id }
            validator(
                ValidatorStringNonEmpty(
                    field = "proposal-id",
                    message = "Proposal ID requested must not be empty"
                )
            )
        }
    }

    execute(CompletePipeline)
})
