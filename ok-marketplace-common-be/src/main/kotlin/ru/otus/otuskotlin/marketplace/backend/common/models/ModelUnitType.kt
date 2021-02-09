package ru.otus.otuskotlin.marketplace.backend.common.models

data class ModelUnitType (
    val id: ModelUnitTypeId = ModelUnitTypeId.NONE,
    val name: String = "",
    val description: String = "",
    val synonyms: MutableSet<String> = mutableSetOf(),
    val symbol: String = "",
    val symbols: MutableSet<String> = mutableSetOf(),
    val isBase: Boolean = false,
) {
    companion object {
        val NONE = ModelUnitType()
    }
}
