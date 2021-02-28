package pages.demands.view

import models.DemandModel
import react.RProps

interface PageDemandViewProps: RProps {
    @JsName("demand")
    var demand: DemandModel
}
