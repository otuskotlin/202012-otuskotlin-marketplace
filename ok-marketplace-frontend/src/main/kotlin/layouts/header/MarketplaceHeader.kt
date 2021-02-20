package layouts.header

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.button.mIconButton
import react.RBuilder
import react.RComponent
import react.rClass

class MarketplaceHeader(props: MarketplaceHeaderProps) :
    RComponent<MarketplaceHeaderProps, MarketplaceHeaderState>(props) {
    override fun RBuilder.render() {
        mAppBar(position = MAppBarPosition.relative) {
            mToolbar {
                mIconButton(iconName = "menu", onClick = {}) {}

                mToolbarTitle("Marketplace")
            }
        }
    }
}

fun RBuilder.marketplaceHeader(props: MarketplaceHeaderProps = MarketplaceHeaderProps()) =
    child(MarketplaceHeader::class.rClass, props = props) {
    }

