package models

data class TechDetModel(
    var id: models.TechDetIdModel = models.TechDetIdModel.Companion.NONE,
    var param: models.TechParamModel = models.TechParamModel.Companion.NONE,
    var value: String = "", // String representation of the value
    var unit: models.UnitTypeModel = models.UnitTypeModel.Companion.NONE,
    var comparableValue: Double = Double.NaN,
) {
    companion object {
        val NONE = models.TechDetModel()
    }
}
