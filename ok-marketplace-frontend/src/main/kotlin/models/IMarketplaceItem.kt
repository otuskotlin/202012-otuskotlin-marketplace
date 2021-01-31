package models

interface IMarketplaceItem {
    val id: IMarketplaceItemId

    companion object {
        val NONE = object : IMarketplaceItem {
            override val id: IMarketplaceItemId = IMarketplaceItemId.NONE
        }
    }
}
