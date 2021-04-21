package ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines

import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.CompletePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.InitializePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.QuerySetWorkMode
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs.DemandFilterStub
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpError
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.operation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline

object DemandFilter : IOperation<MpBeContext> by pipeline({
    execute(InitializePipeline)

    // Установка параметров контекста в зависимости от режима работы в запросе
    execute(QuerySetWorkMode)

    // Обработка стабового запроса
    execute(DemandFilterStub)

    // Получение списка по фильтру из репозитария, ответ сохраняется в контексте
    operation {
        startIf { status == MpBeContextStatus.RUNNING }
        execute {
            try {
                demandRepo.list(this)
                status = MpBeContextStatus.FINISHING
            } catch (t: Throwable) {
                status = MpBeContextStatus.FAILING
                errors.add(
                    MpError(
                        code = "demand-repo-list-error",
                        message = t.message?:"")
                )
            }
        }
    }

    // Подготовка ответа
    execute(CompletePipeline)
})
