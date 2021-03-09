package ru.otus.otuskotlin.marketplace.business.logic

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.kmp.pipelines.pipeline

class DemandFilterPipeline {
    suspend fun run(context: MpBeContext) =
        PIPELINE.run(context.apply {
            // Подготовка контекста (внедрение зависимостей, установка вспомогательных объектов)
        })

    companion object {
        private val PIPELINE = pipeline<MpBeContext> {
            run { status = MpBeContextStatus.RUNNING }

            // Валидация

            // Обработка и работа с БД

            // Подготовка ответа
            run {
                responseDemands = mutableListOf(
                    MpDemandModel(
                        id = MpDemandIdModel("test-id"),
                        avatar = "test-avatar",
                        title = "test-demand",
                        description = "test-description",
                        tagIds = mutableSetOf("1", "2", "3"),
                    )
                )
                status = MpBeContextStatus.SUCCESS
            }
        }
    }
}