package ru.otus.otuskotlin.marketplace.backend.common.models

data class ModelTechParam (
    var id: ModelTechParamId = ModelTechParamId.NONE,
    var name: String = "",
    var description: String = "",
    var priority: Double = Double.MIN_VALUE,
    var units: MutableSet<ModelUnitType> = mutableSetOf()
) {
    companion object {
        val NONE = ModelTechParam()
    }
}
