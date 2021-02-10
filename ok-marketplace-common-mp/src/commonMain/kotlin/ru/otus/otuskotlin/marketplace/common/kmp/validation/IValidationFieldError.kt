package ru.otus.otuskotlin.marketplace.common.kmp.validation

interface IValidationFieldError : IValidationError {
    val field: String
}
