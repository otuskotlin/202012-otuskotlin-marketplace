package app.marketplaceLists.demands.demand

import app.marketplaceLists.items.item.IMarketplaceItemProps
import models.DemandModel

interface MarketplaceDemandProps : IMarketplaceItemProps {
    override var item: DemandModel
}
