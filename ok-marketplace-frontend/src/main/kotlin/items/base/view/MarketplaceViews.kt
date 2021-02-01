package items.base.view

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.styles.Breakpoint
import com.ccfraser.muirwik.components.table.*
import layouts.offers.OffersProps
import layouts.offers.offersLayout
import layouts.tags.TagsProps
import layouts.tags.tagsLayout
import layouts.techdets.TechDetsProps
import layouts.techdets.techDetsLayout
import models.IMarketplaceItem
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.h1
import react.dom.h2
import react.dom.p
import react.dom.span
import react.rClass
import react.router.dom.routeLink

class MarketplaceViews(props: MarketplaceViewsProps) : RComponent<MarketplaceViewsProps, RState>(props) {

    override fun RBuilder.render() {
        val item = props.item ?: return
        val offers = props.offers

        mContainer(maxWidth = Breakpoint.xl) {
            mGridContainer(
                alignContent = MGridAlignContent.flexStart,
                spacing = MGridSpacing.spacing1,
                alignItems = MGridAlignItems.center,
                justify = MGridJustify.flexStart
            ) {
                // Иконка
                if (item.avatar.isNotBlank()) {
                    mGridItem { mAvatar(src = item.avatar) }
                }
                mGridItem {
                    // Title
                    h1 {
                        routeLink(item.linkView) {
                            +(item.title.takeIf { it.isNotBlank() } ?: "Untitled")
                        }

                        // Управление статьей
                        span {
                            routeLink(item.linkEdit) {
                                mIcon("edit")
                            }
                            routeLink(item.linkDelete) {
                                mIcon("delete")
                            }
                        }
                    }
                }
            }

            // Tags
            h2 { +"Тэги" }
            tagsLayout(TagsProps(tags = item.tags))

            // Description
            h2 { +"Описание" }
            mPaper(variant = MPaperVariant.outlined) {
                mContainer(maxWidth = Breakpoint.xl) {
                    p {
                        +"Это конвертер. Огромная бочка для плавки металла"
                    }
                    p {
                        +"Требуется для установки в цеху"
                    }
                }
            }

            if (item.techDets.isNotEmpty()) {
                h2 { +"Спецификация" }
                mPaper {
                    techDetsLayout(TechDetsProps(techDets = item.techDets))
                }
            }

            if (offers?.isNotEmpty() == true) {
                h2 { +"Предложения" }
                mPaper {
                    offersLayout(OffersProps(items = offers))
                }
            }
        }
    }

    class MarketplaceViewConf {
        var item: IMarketplaceItem? = IMarketplaceItem.NONE
        var offers: List<IMarketplaceItem>? = null
    }

}

fun RBuilder.marketplaceView(block: MarketplaceViews.MarketplaceViewConf.() -> Unit) =
    MarketplaceViews.MarketplaceViewConf().also {
        it.block()
        val props = MarketplaceViewsProps(item = it.item, offers = it.offers)
        console.log("RBuilder.marketplaceView", props)
        this@marketplaceView.child(MarketplaceViews::class.rClass, props = props) {
        }
    }
