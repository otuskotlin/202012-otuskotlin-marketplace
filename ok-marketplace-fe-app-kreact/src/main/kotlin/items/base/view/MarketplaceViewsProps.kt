package items.base.view

import models.IMarketplaceItem
import react.RProps

data class MarketplaceViewsProps(
    var item: IMarketplaceItem?,
    var offers: List<IMarketplaceItem>? = null,
): RProps {
}
