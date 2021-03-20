package ru.otus.otuskotlin.marketplace.backend.mappers.kmp.exceptions

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus

class WrongMpBeContextStatus(status: MpBeContextStatus) :
    RuntimeException("Generated status ${status.name} must not appear in transport mappers")
