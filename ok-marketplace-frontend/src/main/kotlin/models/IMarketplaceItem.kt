package models

interface IMarketplaceItem {
    @property:JsName("id")
    val id: IMarketplaceItemId
    @property:JsName("avatar")
    val avatar: String
    @property:JsName("title")
    val title: String

    @property:JsName("linkView")
    val linkView: String
    @property:JsName("linkEdit")
    val linkEdit: String
    @property:JsName("linkDelete")
    val linkDelete: String

    @property:JsName("tags")
    val tags: MutableSet<TagModel>

    companion object {
        val NONE = object : IMarketplaceItem {
            override val id: IMarketplaceItemId = IMarketplaceItemId.NONE
            override val avatar: String = ""
            override val title: String = ""
            override val linkView: String = ""
            override val linkEdit: String = ""
            override val linkDelete: String = ""
            override val tags: MutableSet<TagModel>
                get() = mutableSetOf()
        }
    }
}
