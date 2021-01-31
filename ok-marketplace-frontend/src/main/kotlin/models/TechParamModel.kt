package models

data class TechParamModel(
    var id: TechParamIdModel = TechParamIdModel.NONE,
    var name: String = "",
    var description: String = "",
    var units: MutableSet<UnitTypeModel> = mutableSetOf()
) {
    companion object {
        val NONE = TechParamModel()
    }
}
