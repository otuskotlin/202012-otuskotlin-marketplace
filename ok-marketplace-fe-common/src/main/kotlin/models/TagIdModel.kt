package models

inline class TagIdModel(private val id: String) {
    fun asString(): String = id

    companion object {
        val NONE = models.TagIdModel("")
    }
}
