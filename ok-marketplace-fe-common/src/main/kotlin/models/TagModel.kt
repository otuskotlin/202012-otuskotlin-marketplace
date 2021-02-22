package models

data class TagModel(
    val id: models.TagIdModel = models.TagIdModel.Companion.NONE,
    val title: String = "",
    val avatar: models.TagAvatarModel = models.TagAvatarModel.Companion.NONE,
) {
    companion object {
        val NONE = models.TagModel()
    }
}
