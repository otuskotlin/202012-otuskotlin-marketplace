package ru.otus.otuskotlin.marketplace.common.backend.models

enum class MpWorkMode {
    PROD,
    TEST;

    companion object {
        val DEFAULT = PROD
    }
}
