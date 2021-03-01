package ru.otus.otuskotlin.marketplace.common.kmp.validation

data class ValidationDefaultError(
    override val message: String,
) : IValidationError
