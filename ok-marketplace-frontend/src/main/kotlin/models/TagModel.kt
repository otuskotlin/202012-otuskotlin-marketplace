package models

data class TagModel(
    val id: TagIdModel = TagIdModel.NONE,
    val title: String = "",
    val avatar: TagAvatarModel = TagAvatarModel.NONE,
) {
    companion object {
        val NONE = TagModel()
    }
}
