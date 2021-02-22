package models

data class TagAvatarModel(
    val src: String = "",
    val type: models.TagAvatarModel.TagAvatarType = models.TagAvatarModel.TagAvatarType.NONE,
) {
    enum class TagAvatarType {
        NONE,
        ICON,
        IMAGE
    }

    companion object {
        val NONE = models.TagAvatarModel("", models.TagAvatarModel.TagAvatarType.NONE)
    }
}
