package ru.otus.otuskotlin.marketplace.pipelines.kmp

interface IOperationBuilder<T> {
    fun build(): IOperation<T>
}
