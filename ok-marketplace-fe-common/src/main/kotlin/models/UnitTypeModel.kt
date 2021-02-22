package models

data class UnitTypeModel(
    var id: models.UnitTypeIdModel = models.UnitTypeIdModel.Companion.NONE,
    var name: String = "",
    var description: String = "",
    var synonyms: MutableSet<String> = mutableSetOf(),
    var symbol: String = "",
    var symbols: MutableSet<String> = mutableSetOf(),
    var isBase: Boolean = false,
) {
    companion object {
        val NONE = models.UnitTypeModel()
    }
}
