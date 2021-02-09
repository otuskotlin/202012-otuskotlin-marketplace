package ru.otus.otuskotlin.marketplace.backend.common.models

data class MpTechParamModel (
    var id: MpTechParamIdModel = MpTechParamIdModel.NONE,
    var name: String = "",
    var description: String = "",
    var priority: Double = Double.MIN_VALUE,
    var units: MutableSet<MpUnitTypeModel> = mutableSetOf()
) {
    companion object {
        val NONE = MpTechParamModel()
    }
}
