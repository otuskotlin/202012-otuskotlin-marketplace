package ru.otus.otuskotlin.marketplace.transport.logs

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
    val errors: List<ErrorLog>? = null,
)
