package models

inline class ProposalIdModel(override val id: String): models.IMarketplaceItemId {

    fun asString(): String = id
    companion object {
        val NONE = models.ProposalIdModel("")
    }
}
