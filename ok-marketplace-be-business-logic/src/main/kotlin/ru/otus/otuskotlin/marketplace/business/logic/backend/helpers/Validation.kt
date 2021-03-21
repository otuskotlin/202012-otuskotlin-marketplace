package ru.otus.otuskotlin.marketplace.business.logic.backend.helpers

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpError
import ru.otus.otuskotlin.marketplace.common.kmp.validation.ValidationResult
import ru.otus.otuskotlin.marketplace.kmp.pipelines.validation.ValidationBuilder
import ru.otus.otuskotlin.marketplace.pipelines.kmp.Pipeline

fun Pipeline.Builder<MpBeContext>.validation(block: ValidationBuilder<MpBeContext>.() -> Unit) {
    execute(ValidationBuilder<MpBeContext>()
        .apply {
            startIf { status == MpBeContextStatus.RUNNING }
            errorHandler { vr: ValidationResult ->
                if (vr.isSuccess) return@errorHandler
                val errs = vr.errors.map { MpError(message = it.message) }
                errors.addAll(errs)
                status = MpBeContextStatus.FAILING
            }
        }
        .apply(block)
        .build())
}
