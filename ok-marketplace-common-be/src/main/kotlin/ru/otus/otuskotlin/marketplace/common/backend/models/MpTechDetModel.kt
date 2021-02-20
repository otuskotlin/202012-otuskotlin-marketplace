package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpTechDetModel(
    val id: MpTechDetIdModel = MpTechDetIdModel.NONE,
    val param: MpTechParamModel = MpTechParamModel.NONE,
    val value: String = "", // String representation of the value
    val unit: MpUnitTypeModel = MpUnitTypeModel.NONE,
    val comparableValue: Double = Double.NaN,
) {
        companion object {
            val NONE = MpTechDetModel()
        }
}
