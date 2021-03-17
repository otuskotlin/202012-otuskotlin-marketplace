package ru.otus.otuskotlin.marketplace.common.backend.context

enum class MpBeContextStatus {
    NONE,
    RUNNING,
    FINISHING,
    FAILING,
    SUCCESS,
    ERROR;

    val isError: Boolean
        get() = this in setOf(FAILING, ERROR)
}