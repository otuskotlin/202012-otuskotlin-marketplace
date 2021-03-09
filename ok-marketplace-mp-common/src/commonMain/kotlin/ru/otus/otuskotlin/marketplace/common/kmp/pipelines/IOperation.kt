package ru.otus.otuskotlin.marketplace.common.kmp.pipelines

interface IOperation<T> {
    suspend fun run(context: T)
}