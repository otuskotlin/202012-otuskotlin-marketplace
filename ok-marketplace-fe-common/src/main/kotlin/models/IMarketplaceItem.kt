package models

interface IMarketplaceItem {
    @property:JsName("id")
    val id: models.IMarketplaceItemId
    @property:JsName("avatar")
    val avatar: String
    @property:JsName("title")
    val title: String
    @property:JsName("description")
    val description: String

    @property:JsName("linkView")
    val linkView: String
    @property:JsName("linkEdit")
    val linkEdit: String
    @property:JsName("linkDelete")
    val linkDelete: String

    @property:JsName("tagIds")
    val tagIds: MutableSet<TagIdModel>

    @property:JsName("tags")
    val tags: MutableSet<models.TagModel>

    @property:JsName("techDets")
    val techDets: MutableSet<models.TechDetModel>

    companion object {
        val NONE = object : models.IMarketplaceItem {
            override val id: models.IMarketplaceItemId = models.IMarketplaceItemId.Companion.NONE
            override val avatar: String = ""
            override val title: String = ""
            override val description: String = ""
            override val linkView: String = ""
            override val linkEdit: String = ""
            override val linkDelete: String = ""
            override val tagIds: MutableSet<models.TagIdModel>
                get() = mutableSetOf()
            override val tags: MutableSet<models.TagModel>
                get() = mutableSetOf()
            override val techDets: MutableSet<models.TechDetModel>
                get() = mutableSetOf()
        }
    }
}
