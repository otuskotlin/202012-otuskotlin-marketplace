package app.marketplaceLists.demands.demand

import app.marketplaceLists.IMarketplaceItemProps
import models.DemandModel

interface MarketplaceDemandProps : IMarketplaceItemProps {
    override var item: DemandModel
}
