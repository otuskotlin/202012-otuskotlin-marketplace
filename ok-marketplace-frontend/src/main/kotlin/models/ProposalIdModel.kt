package models

inline class ProposalIdModel(override val id: String): IMarketplaceItemId {

    override fun toString(): String = id
    companion object {
        val NONE = ProposalIdModel("")
    }
}
