package models

interface IMarketplaceItemId {
    @property:JsName("id")
    val id: String

    override fun toString(): String

    companion object {
        val NONE = object : models.IMarketplaceItemId {
            override val id: String = ""

            override fun toString() = id

        }
    }
}
