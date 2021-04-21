package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpDemandFilterModel(
    val text: String = "",
    val includeDescription: Boolean = false,
    val sortBy: MpSortModel = MpSortModel.NONE,
    val offset: Int = Int.MIN_VALUE,
    val count: Int = Int.MIN_VALUE,
) {
    companion object {
        val NONE = MpDemandFilterModel()
    }
}
