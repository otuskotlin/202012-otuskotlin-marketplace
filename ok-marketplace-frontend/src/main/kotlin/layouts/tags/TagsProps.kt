package layouts.tags

import models.TagModel
import react.RProps

data class TagsProps(
    var tags: MutableSet<TagModel> = mutableSetOf()
): RProps {

}
