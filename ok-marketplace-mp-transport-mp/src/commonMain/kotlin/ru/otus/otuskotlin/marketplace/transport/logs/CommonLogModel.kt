package ru.otus.otuskotlin.marketplace.transport.logs

import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto

/**
 *  Общая модель лога для всех микросервисов системы
 */
data class CommonLogModel(
    val messageId: String? = null,
    val messageTime: String? = null,
    val logId: String? = null,
    val source: String? = null,
    val marketplace: MpLogModel? = null,
    // поля для других сервисов
    val errors: List<ErrorDto>? = null,
)
