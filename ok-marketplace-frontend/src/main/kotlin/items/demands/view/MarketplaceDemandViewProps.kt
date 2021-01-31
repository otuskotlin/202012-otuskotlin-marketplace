package items.demands.view

import models.DemandModel
import react.RProps

data class MarketplaceDemandViewProps(
    @JsName("demand")
    var demand: DemandModel?
): RProps {
}
