package ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines

import ru.otus.otuskotlin.marketplace.business.logic.backend.helpers.validation
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.CompletePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.InitializePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs.DemandUpdateStub
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.kmp.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline

object DemandUpdate : IOperation<MpBeContext> by pipeline({
    execute(InitializePipeline)

    execute(DemandUpdateStub)

    validation {
        validate<String?> {
            validator(ValidatorStringNonEmpty(field = "id", message = "You must provide non-empty id for the demand"))
            on { requestDemand.id.asString() }
        }
        validate<String?> {
            validator(ValidatorStringNonEmpty(field = "title", message = "You must provide non-empty title for the demand"))
            on { requestDemand.title }
        }
        validate<String?> {
            validator(ValidatorStringNonEmpty(field = "description", message = "You must provide non-empty description for the demand"))
            on { requestDemand.description }
        }
    }

    execute(CompletePipeline)
})
