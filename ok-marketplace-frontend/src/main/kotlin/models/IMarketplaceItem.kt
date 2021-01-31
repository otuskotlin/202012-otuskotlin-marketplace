package models

interface IMarketplaceItem {
    @property:JsName("id")
    val id: IMarketplaceItemId
    @property:JsName("avatar")
    val avatar: String

    companion object {
        val NONE = object : IMarketplaceItem {
            override val id: IMarketplaceItemId = IMarketplaceItemId.NONE
            override val avatar: String = ""
        }
    }
}
