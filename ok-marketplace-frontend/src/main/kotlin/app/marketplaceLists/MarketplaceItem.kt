package app.marketplaceLists

import com.ccfraser.muirwik.components.MTypographyVariant
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.card.mCard
import com.ccfraser.muirwik.components.card.mCardActionArea
import com.ccfraser.muirwik.components.card.mCardActions
import com.ccfraser.muirwik.components.card.mCardContent
import com.ccfraser.muirwik.components.mTypography
import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import react.RBuilder
import react.RComponent
import react.RState
import styled.css

abstract class MarketplaceItem<P: IMarketplaceItemProps, S: RState>(
    props: P,
) : RComponent<P, S>(props) {

    protected val history = props.history
    private var routeToView = ""
    private var title = ""
    private val actions = mutableListOf<MarketplaceItemAction>()

    private fun RBuilder.renderItem() {
        mCard(raised = true) {
            css {
                width = 10.em
                margin(all = 0.5.em)
                transition(duration = 0.3.s)
                boxShadow(
                    offsetX = 0.px,
                    offsetY = 8.px,
                    blurRadius = 40.px,
                    spreadRadius = (-12).px,
                    color = rgba(0, 0, 0, 0.3)
                )
                hover {
                    boxShadow(
                        offsetX = 0.px,
                        offsetY = 16.px,
                        blurRadius = 70.px,
                        spreadRadius = (-12.125).px,
                        color = rgba(0, 0, 0, 0.3)
                    )
                }
            }
            mCardContent {
                mCardActionArea {
                    attrs.onClick = { history.push(routeToView) }
                    mTypography(variant = MTypographyVariant.h6) {
                        +title
                    }
                }
                mCardActions {
                    actions.forEach { action ->
                        mIconButton(action.iconName, onClick = { history.push(action.route) })
                    }
                }
            }
        }
    }

    protected fun RBuilder.marketplaceItem(block: MarketplaceItemConfig.() -> Unit) {
        val config = MarketplaceItemConfig().apply(block)
        routeToView = config.itemViewRoute
        title = config.itemTitle
        actions.addAll(config.actions)

        renderItem()
    }

    class MarketplaceItemConfig {
        var itemViewRoute: String = "/"
        var itemTitle: String = ""
        private val _actions: MutableList<MarketplaceItemAction> = mutableListOf()
        val actions: List<MarketplaceItemAction>
            get() = _actions.toList()

        fun addAction(iconName: String, route: String) {
            _actions.add(
                MarketplaceItemAction(
                iconName = iconName,
                route = route
            )
            )
        }

    }

    data class MarketplaceItemAction(
        val iconName: String,
        val route: String,
    )

}
