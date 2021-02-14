package ru.otus.otuskotlin.marketplace.common.kmp.validation

interface IValidator<T> {
    infix fun validate(sample: T): ValidationResult
}
