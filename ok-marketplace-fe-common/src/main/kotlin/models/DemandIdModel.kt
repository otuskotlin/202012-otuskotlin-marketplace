package models

inline class DemandIdModel(override val id: String): models.IMarketplaceItemId {

    fun asString(): String = id
    companion object {
        val NONE = models.DemandIdModel("")
    }
}
