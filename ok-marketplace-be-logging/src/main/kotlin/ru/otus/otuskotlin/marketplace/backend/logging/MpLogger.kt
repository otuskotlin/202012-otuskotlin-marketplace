package ru.otus.otuskotlin.marketplace.backend.logging

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory

fun mpLogger(loggerId: String): MpLogContext = mpLogger(
    logger = LoggerFactory.getLogger(loggerId) as Logger
)

fun mpLogger(clazz: Class<out Any>): MpLogContext = mpLogger(
    logger = LoggerFactory.getLogger(clazz) as Logger
)

/**
 * Функция генерирует внутренний "логер" - объект класса MpLogContext
 *
 * @param logger интстанс логера Logback, полученный методом [[LoggerFactory.getLogger()]]
 */
fun mpLogger(logger: Logger): MpLogContext = MpLogContext(
    logger = logger,
    loggerId = logger.name,
)
