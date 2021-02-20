package models

inline class DemandIdModel(override val id: String): IMarketplaceItemId {

    override fun toString(): String = id
    companion object {
        val NONE = DemandIdModel("")
    }
}
