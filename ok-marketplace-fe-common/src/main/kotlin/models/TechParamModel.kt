package models

data class TechParamModel(
    var id: models.TechParamIdModel = models.TechParamIdModel.Companion.NONE,
    var name: String = "",
    var description: String = "",
    var priority: Double = Double.MIN_VALUE,
    var units: MutableSet<models.UnitTypeModel> = mutableSetOf()
) {
    companion object {
        val NONE = models.TechParamModel()
    }
}
