package demands.demand

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.card.*
import kotlinx.css.*
import kotlinx.css.properties.*
import react.*
import styled.css

class MarketplaceDemand(props: MarketplaceDemandProps) : RComponent<MarketplaceDemandProps, MarketplaceDemandState>(props) {
    override fun RBuilder.render() {
        mCard(raised = true) {
            css {
                width = 100.pc
                margin(all = 15.px)
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
                mTypography(variant = MTypographyVariant.h6) {
                    +"Some demand"
                }
//                mDivider(variant = MDividerVariant.inset)
                mCardActions {
//                    mCardActionArea {
//                        mIcon("edit")
//                        attrs {
//                            onClick = {
//                                console.log("Edit button is clicked")
//                            }
//                        }
//                    }
                    mIconButton("edit", onClick = {
                        console.log("Edit button is clicked")
                    })
                    mIconButton("preview", onClick = {
                        console.log("View button is clicked")
                    })
                    mIconButton("list", onClick = {
                        console.log("Offers button is clicked")
                    })
                }
            }
        }
    }

}

class MarketplaceDemandState : RState {

}

class MarketplaceDemandProps : RProps {

}

fun RBuilder.marketplaceDemand(props: MarketplaceDemandProps = MarketplaceDemandProps()) =
    child(MarketplaceDemand::class.rClass, props = props) {}
