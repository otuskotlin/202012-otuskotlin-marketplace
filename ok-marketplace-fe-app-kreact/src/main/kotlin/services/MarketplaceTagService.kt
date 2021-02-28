package services

import models.TagAvatarModel
import models.TagIdModel
import models.TagModel

class MarketplaceTagService {
    suspend fun resolveTags(tagIds: MutableSet<TagIdModel>): Set<TagModel> = tagIds.map { resolveTag(it) }.toSet()

    suspend fun resolveTag(tagId: TagIdModel) = TagModel(
        id = tagId,
        title = "Tag ${tagId.asString()}",
        avatar = TagAvatarModel("menu", type = TagAvatarModel.TagAvatarType.ICON)
    )

}
