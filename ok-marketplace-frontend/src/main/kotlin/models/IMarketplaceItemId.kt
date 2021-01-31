package models

interface IMarketplaceItemId {
    val id: String

    override fun toString(): String

    companion object {
        val NONE = object : IMarketplaceItemId {
            override val id: String = ""

            override fun toString() = id

        }
    }
}
