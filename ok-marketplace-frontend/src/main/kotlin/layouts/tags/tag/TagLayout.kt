package layouts.tags.tag

import com.ccfraser.muirwik.components.mAvatar
import com.ccfraser.muirwik.components.mChip
import com.ccfraser.muirwik.components.mIcon
import models.TagAvatarModel
import react.RBuilder
import react.RComponent
import react.RState
import react.rClass

class TagLayout(props: TagProps) : RComponent<TagProps, RState>() {
    override fun RBuilder.render() {
        val tag = props.tag
        mChip(
            label = tag.title,
            avatar = when (tag.avatar.type) {
                TagAvatarModel.TagAvatarType.ICON -> mAvatar(addAsChild = false) { mIcon(tag.avatar.src) }
                TagAvatarModel.TagAvatarType.IMAGE -> mAvatar(src = tag.avatar.src)
                else -> null
            }
        )
    }
}

fun RBuilder.tagLayout(props: TagProps) = child(TagLayout::class.rClass, props = props) {

}
