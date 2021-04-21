package ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines

import ru.otus.otuskotlin.marketplace.business.logic.backend.helpers.validation
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.CompletePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.InitializePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.QuerySetWorkMode
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs.DemandCreateStub
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpError
import ru.otus.otuskotlin.marketplace.common.kmp.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.operation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline

object DemandCreate : IOperation<MpBeContext> by pipeline({
    execute(InitializePipeline)

    // Установка параметров контекста в зависимости от режима работы в запросе
    execute(QuerySetWorkMode)

    // Обработка стабового запроса
    execute(DemandCreateStub)

    // Валидация параметров запроса
    validation {
        validate<String?> {
            validator(ValidatorStringNonEmpty(field = "title", message = "You must provide non-empty title for the demand"))
            on { requestDemand.title }
        }
        validate<String?> {
            validator(ValidatorStringNonEmpty(field = "description", message = "You must provide non-empty description for the demand"))
            on { requestDemand.description }
        }
    }

    // Добавление в репозитарий, ответ сохраняется в контексте
    operation {
        startIf { status == MpBeContextStatus.RUNNING }
        execute {
            try {
                demandRepo.create(this)
                status = MpBeContextStatus.FINISHING
            } catch (t: Throwable) {
                status = MpBeContextStatus.FAILING
                errors.add(
                    MpError(
                        code = "demand-repo-create-error",
                        message = t.message?:"")
                )
            }
        }
    }

    // Подготовка ответа
    execute(CompletePipeline)
})
