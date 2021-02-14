package models

data class TagAvatarModel(
    val src: String = "",
    val type: TagAvatarType = TagAvatarType.NONE,
) {
    enum class TagAvatarType {
        NONE,
        ICON,
        IMAGE
    }

    companion object {
        val NONE = TagAvatarModel("", TagAvatarType.NONE)
    }
}
