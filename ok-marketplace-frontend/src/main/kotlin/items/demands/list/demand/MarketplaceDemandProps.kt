package items.demands.list.demand

import items.base.list.IMarketplaceItemProps
import models.DemandModel

interface MarketplaceDemandProps : IMarketplaceItemProps {
    override var item: DemandModel
}
