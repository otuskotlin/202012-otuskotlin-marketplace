package models

data class TechDetModel(
    var id: TechDetIdModel = TechDetIdModel.NONE,
    var param: TechParamModel = TechParamModel.NONE,
    var value: String = "", // String representation of the value
    var unit: UnitTypeModel = UnitTypeModel.NONE,
    var comparableValue: Double = Double.NaN,
) {
    companion object {
        val NONE = TechDetModel()
    }
}
