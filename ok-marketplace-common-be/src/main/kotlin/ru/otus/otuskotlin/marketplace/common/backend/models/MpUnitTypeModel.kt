package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpUnitTypeModel (
    val id: MpUnitTypeIdModel = MpUnitTypeIdModel.NONE,
    val name: String = "",
    val description: String = "",
    val synonyms: MutableSet<String> = mutableSetOf(),
    val symbol: String = "",
    val symbols: MutableSet<String> = mutableSetOf(),
    val isBase: Boolean = false,
) {
    companion object {
        val NONE = MpUnitTypeModel()
    }
}
