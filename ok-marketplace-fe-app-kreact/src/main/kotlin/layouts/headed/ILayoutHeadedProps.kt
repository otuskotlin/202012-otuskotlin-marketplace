package layouts.headed

import react.RBuilder
import react.RProps

interface ILayoutHeadedProps : RProps {
    var pageMainBody: (RBuilder.() -> Unit)?
}
