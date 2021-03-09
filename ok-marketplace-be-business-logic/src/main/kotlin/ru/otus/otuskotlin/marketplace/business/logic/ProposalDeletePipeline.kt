package ru.otus.otuskotlin.marketplace.business.logic

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.common.kmp.pipelines.pipeline

class ProposalDeletePipeline {
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
                responseProposal = MpProposalModel(
                    id = MpProposalIdModel("test-id"),
                    avatar = "test-avatar",
                    title = "test-proposal",
                    description = "test-description",
                    tagIds = mutableSetOf("1", "2", "3"),
                )
                status = MpBeContextStatus.SUCCESS
            }
        }
    }
}