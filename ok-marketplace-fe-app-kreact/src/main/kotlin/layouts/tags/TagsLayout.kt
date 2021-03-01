package layouts.tags

import com.ccfraser.muirwik.components.*
import layouts.tags.tag.TagLayout
import layouts.tags.tag.TagProps
import layouts.tags.tag.tagLayout
import react.RBuilder
import react.RComponent
import react.rClass

class TagsLayout(props: TagsProps): RComponent<TagsProps, TagsState>() {

    override fun RBuilder.render() {
        mGridContainer(
            alignContent = MGridAlignContent.flexStart,
            spacing = MGridSpacing.spacing1,
            alignItems = MGridAlignItems.flexStart,
            justify = MGridJustify.flexStart
        ) {
            val tags = props.tags

            tags.forEach {
                mGridItem {
                    tagLayout(TagProps(tag = it))
                }
            }
        }
    }
}

fun RBuilder.tagsLayout(props: TagsProps) = child(TagsLayout::class.rClass, props = props) {

}
