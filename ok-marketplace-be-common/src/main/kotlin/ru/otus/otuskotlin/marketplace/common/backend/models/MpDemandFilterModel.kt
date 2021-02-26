package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpDemandFilterModel(
    val text: String = ""
) {
    companion object {
        val NONE = MpDemandFilterModel()
    }
}
