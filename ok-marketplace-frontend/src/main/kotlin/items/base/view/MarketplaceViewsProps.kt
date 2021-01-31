package items.base.view

import models.IMarketplaceItem
import react.RProps

data class MarketplaceViewsProps(
    @JsName("item")
    var item: IMarketplaceItem?
): RProps {
}
