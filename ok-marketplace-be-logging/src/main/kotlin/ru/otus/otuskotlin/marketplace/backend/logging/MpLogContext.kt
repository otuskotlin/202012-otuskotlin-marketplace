package ru.otus.otuskotlin.marketplace.backend.logging

import ch.qos.logback.classic.Logger
import org.slf4j.Marker
import org.slf4j.event.Level
import org.slf4j.event.LoggingEvent
import java.time.Instant


data class MpLogContext(
    /**
     * Объект логера Logback.
     */
    val logger: Logger,
    /**
     * Идентификатор логера. Пробрасывается в Logback и замещает [[loggerClass]]. Также используется в сообщения
     * логера о входе и выходе из функции.
     */
    val loggerId: String = "",
) {
    /**
     * Основная функция для логирования
     */
    fun log(
        msg: String = "",
        level: Level = Level.TRACE,
        marker: Marker = DefaultMarker("DEV"),
        e: Throwable? = null,
        vararg objs: Any?
    ) = logger.log(
        object : LoggingEvent {
            override fun getThrowable() = e
            override fun getTimeStamp(): Long = Instant.now().toEpochMilli()
            override fun getThreadName(): String = Thread.currentThread().name
            override fun getMessage(): String = msg
            override fun getMarker(): Marker = marker
            override fun getArgumentArray(): Array<out Any> = objs.filterNotNull().toTypedArray()

            override fun getLevel(): Level = level
            override fun getLoggerName(): String = logger.name
        }
    )

    /**
     * Функция обертка для выполнения прикладного кода с логированием перед выполнением и после
     */
    fun <T> doWithLogging(
            logId: String = "",
            marker: Marker = DefaultMarker("DEV"),
            level: Level = Level.INFO,
            block: () -> T,
        ): T = try {
                    log(
                        msg = "$loggerId Entering $logId",
                        level = level,
                        marker = DefaultMarker("START", listOf(marker))
                    )
                    val result = block()
                    log(
                        msg = "$loggerId Finishing $logId",
                        level = level,
                        marker = DefaultMarker("END", listOf(marker))
                    )
                    result
                } catch (e: Throwable) {
                    log(
                        msg = "$loggerId Failing $logId",
                        level = Level.ERROR,
                        marker = DefaultMarker("ERROR", listOf(marker)),
                        e = e,
                    )
                    throw e
                }

    /**
     * Функция обертка для выполнения прикладного кода с логированием ошибки
     */
    fun <T> doWithErrorLogging(
            logId: String = "",
            marker: Marker = DefaultMarker("DEV"),
            needThrow: Boolean = true,
            block: () -> T,
        ): T? = try {
                    val result = block()
                    result
                } catch (e: Throwable) {
                    log(
                        msg = "$loggerId Failing $logId",
                        level = Level.ERROR,
                        marker = DefaultMarker("ERROR", listOf(marker)),
                        e = e,
                    )
                    if (needThrow)
                        throw e
                    else
                        null
                }
}
