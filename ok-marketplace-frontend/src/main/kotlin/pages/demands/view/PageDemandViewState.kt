package pages.demands.view

import models.DemandModel
import react.RState

interface PageDemandViewState: RState {
    var demand: DemandModel?
}
