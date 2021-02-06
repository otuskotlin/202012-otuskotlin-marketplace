package ru.otus.otuskotlin.marketplace.transport.kmp.models.responses

import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.requests.IRequestMP

interface IResponseMP<T, R>: IRequestMP<T> {
    val responseData: R?
    val errors: List<ErrorDto>?
    val status: ResponseStatusDto?
}
