package ru.otus.otuskotlin.marketplace.backend.common.models

data class ModelTechDet(
    val id: ModelTechDetId = ModelTechDetId.NONE,
    val param: ModelTechParam = ModelTechParam.NONE,
    val value: String = "", // String representation of the value
    val unit: ModelUnitType = ModelUnitType.NONE,
    val comparableValue: Double = Double.NaN,
) {
        companion object {
            val NONE = ModelTechDet()
        }
}
