package items.base.list

import models.IMarketplaceItem
import react.RProps
import react.router.dom.RouteResultProps

interface IMarketplaceItemProps : RouteResultProps<RProps> {
    val item: IMarketplaceItem
}
