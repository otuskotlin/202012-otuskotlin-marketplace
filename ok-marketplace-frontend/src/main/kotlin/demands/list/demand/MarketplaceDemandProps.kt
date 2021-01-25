package demands.list.demand

import items.list.IMarketplaceItemProps
import models.DemandModel

interface MarketplaceDemandProps : IMarketplaceItemProps {
    override var item: DemandModel
}
