package layouts.offers

import models.IMarketplaceItem
import react.RProps

data class OffersProps(
    val items: List<IMarketplaceItem> = listOf()
): RProps {

}
