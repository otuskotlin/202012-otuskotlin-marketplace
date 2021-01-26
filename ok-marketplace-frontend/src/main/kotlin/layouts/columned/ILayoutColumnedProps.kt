package layouts.columned

import react.RBuilder
import react.RProps

interface ILayoutColumnedProps : RProps {
    var blockDemands: (RBuilder.() -> Unit)?
    var blockProposals: (RBuilder.() -> Unit)?
}
