package ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines

import ru.otus.otuskotlin.marketplace.business.logic.backend.helpers.validation
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.CompletePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.InitializePipeline
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.QuerySetWorkMode
import ru.otus.otuskotlin.marketplace.business.logic.backend.operations.stubs.DemandReadStub
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.IMpError
import ru.otus.otuskotlin.marketplace.common.backend.models.MpError
import ru.otus.otuskotlin.marketplace.common.backend.models.MpPrincipalModel
import ru.otus.otuskotlin.marketplace.common.kmp.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.operation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline
import kotlin.math.E

object DemandRead : IOperation<MpBeContext> by pipeline({
    execute(InitializePipeline)

    // Установка параметров контекста в зависимости от режима работы в запросе
    execute(QuerySetWorkMode)

    // Обработка стабового запроса
    execute(DemandReadStub)

    // Валидация учетных данных
    operation {
        startIf { status == MpBeContextStatus.RUNNING }
        execute {
            if (principal == MpPrincipalModel.NONE) {
                errors.add(
                    MpError(
                        code = "unauthorized",
                        group = IMpError.Group.AUTH,
                        level = IMpError.Level.ERROR,
                        message = "User is unauthorized"
                    )
                )
                status = MpBeContextStatus.ERROR
            }
        }
    }

    // demandId = "test-demand-1"
    // userId = "test-user-1"
    // if (demand.owner.id == userId) {
    // permissions.add(PERMISSTION_UPDATE)
    // permissions.add(PERMISSTION_UPDATE)

    // Валидация параметров запроса
    validation {
        validate<String?> {
            on { requestDemandId.id }
            validator(ValidatorStringNonEmpty(
                field = "demand-id",
                message = "Demand ID requested must not be empty"
            ))
        }
    }

    // Чтение данных из репозитария, ответ сохраняется в контексте
    operation {
        startIf { status == MpBeContextStatus.RUNNING }
        execute {
            try {
                demandRepo.read(this)
                status = MpBeContextStatus.FINISHING
            } catch (t: Throwable) {
                status = MpBeContextStatus.FAILING
                errors.add(
                    MpError(
                    code = "demand-repo-read-error",
                    message = t.message?:"")
                )
            }
        }
    }

    // Подготовка ответа
    execute(CompletePipeline)
})
