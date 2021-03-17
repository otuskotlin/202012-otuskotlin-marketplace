package ru.otus.otuskotlin.marketplace.pipelines.kmp

interface IOperation<T> {
    suspend fun execute(context: T)
}