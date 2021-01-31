package items.base.view

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.styles.Breakpoint
import com.ccfraser.muirwik.components.table.*
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

        mContainer(maxWidth = Breakpoint.xl) {
            mGridContainer(
                alignContent = MGridAlignContent.flexStart,
                spacing = MGridSpacing.spacing1,
                alignItems = MGridAlignItems.center,
                justify = MGridJustify.flexStart
            ) {
                // Иконка
                console.log("AVATAR", item, item.avatar, item.id)
                if (item.avatar.isNotBlank()) {
                    mGridItem { mAvatar(src = item.avatar) }
                }
                mGridItem {
                    // Title
                    h1 {
                        routeLink("/demand/demand-Converter") {
                            +"Конвертер"
                        }

                        // Управление статьей
                        span {
                            routeLink("/demand/deman-Converter/edit") {
                                mIcon("edit")
                            }
                            routeLink("/demand/deman-Converter/delete") {
                                mIcon("delete")
                            }
                        }
                    }
                }
            }

            // Tags
            h2 { +"Тэги" }
            mGridContainer(
                alignContent = MGridAlignContent.flexStart,
                spacing = MGridSpacing.spacing1,
                alignItems = MGridAlignItems.flexStart,
                justify = MGridJustify.flexStart
            ) {
                mGridItem {
                    mChip(
                        label = "Металлургия",
                        avatar = mAvatar(addAsChild = false) { mIcon("build_outlined") },
                    )
                }
                mGridItem {
                    mChip(
                        label = "Оборудование",
                        avatar = mAvatar(addAsChild = false) { mIcon("build_outlined") },
                    )
                }
                mGridItem {
                    mChip(
                        label = "Б/У",
                        avatar = mAvatar(addAsChild = false) { mIcon("build_outlined") },
                    )
                }
            }

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

            h2 { +"Спецификация" }
            mPaper {
                mGridContainer(
                    spacing = MGridSpacing.spacing2,
                    alignItems = MGridAlignItems.flexStart,
                    alignContent = MGridAlignContent.stretch,
                    justify = MGridJustify.flexStart
                ) {
                    mGridItem(
                        lg = MGridSize.cells3,
                        md = MGridSize.cells4,
                        sm = MGridSize.cells6,
                        xs = MGridSize.cells12
                    ) {
                        mTableContainer {
                            mTable {
                                mTableHead {
                                    mTableRow {
                                        mTableCell(variant = MTableCellVariant.head) { +"Параметр" }
                                        mTableCell(variant = MTableCellVariant.head) { +"Значение" }
                                    }
                                }
                                mTableBody {
                                    mTableRow {
                                        mTableCell { +"Цена" }
                                        mTableCell(align = MTableCellAlign.inherit) { +"25 000 000 Руб" }
                                    }
                                    mTableRow {
                                        mTableCell { +"Цена с доставкой" }
                                        mTableCell(align = MTableCellAlign.inherit) { +"26 000 000 Руб" }
                                    }
                                }
                            }
                        }
                    }
                    mGridItem(
                        lg = MGridSize.cells3,
                        md = MGridSize.cells4,
                        sm = MGridSize.cells6,
                        xs = MGridSize.cells12
                    ) {
                        mTableContainer {
                            mTable {
                                mTableHead {
                                    mTableRow {
                                        mTableCell(variant = MTableCellVariant.head) { +"Параметр" }
                                        mTableCell(variant = MTableCellVariant.head) { +"Значение" }
                                    }
                                }
                                mTableBody {
                                    mTableRow {
                                        mTableCell { +"Масса" }
                                        mTableCell(align = MTableCellAlign.inherit) { +"15 т" }
                                    }
                                }
                            }
                        }
                    }
                    mGridItem(
                        lg = MGridSize.cells3,
                        md = MGridSize.cells4,
                        sm = MGridSize.cells6,
                        xs = MGridSize.cells12
                    ) {
                        mTableContainer {
                            mTable {
                                mTableHead {
                                    mTableRow {
                                        mTableCell(variant = MTableCellVariant.head) { +"Параметр" }
                                        mTableCell(variant = MTableCellVariant.head) { +"Значение" }
                                    }
                                }
                                mTableBody {
                                    mTableRow {
                                        mTableCell { +"Материал корпуса" }
                                        mTableCell(align = MTableCellAlign.inherit) { +"Сталь" }
                                    }
                                }
                            }
                        }
                    }
                    mGridItem(
                        lg = MGridSize.cells3,
                        md = MGridSize.cells4,
                        sm = MGridSize.cells6,
                        xs = MGridSize.cells12
                    ) {
                        mTableContainer {
                            mTable {
                                mTableHead {
                                    mTableRow {
                                        mTableCell(variant = MTableCellVariant.head) { +"Параметр" }
                                        mTableCell(variant = MTableCellVariant.head) { +"Значение" }
                                    }
                                }
                                mTableBody {
                                    mTableRow {
                                        mTableCell { +"Материал футеровки" }
                                        mTableCell(align = MTableCellAlign.inherit) { +"Шамотный кирпич" }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            h2 { +"Предложения" }
            mPaper {
                mTableContainer {
                    mTable {
                        mTableHead {
                            mTableRow {
                                mTableCell(variant = MTableCellVariant.head) { +"#" }
                                mTableCell(variant = MTableCellVariant.head) { +"Name" }
                                mTableCell(variant = MTableCellVariant.head) { +"Производитель" }
                                mTableCell(variant = MTableCellVariant.head) { +"Поставщик" }
                            }
                        }
                        mTableBody {
                            mTableRow {
                                mTableCell { +"1" }
                                mTableCell { +"Большой сталелитейный конвертер" }
                                mTableCell { +"ООО \"МетКонв\"" }
                                mTableCell { +"ООО \"МетКонв\"" }
                            }
                            mTableRow {
                                mTableCell { +"2" }
                                mTableCell { +"Средний сталелитейный конвертер" }
                                mTableCell { +"ООО \"МетКонв\"" }
                                mTableCell { +"ООО \"МетКонв\"" }
                            }
                        }
                    }
                }
            }
        }
    }

    class MarketplaceViewConf {
        var item: IMarketplaceItem? = IMarketplaceItem.NONE
    }
}

fun RBuilder.marketplaceView(block: MarketplaceViews.MarketplaceViewConf.() -> Unit) =
    MarketplaceViews.MarketplaceViewConf().also {
        it.block()
        val props = MarketplaceViewsProps(item = it.item)
        console.log("RBuilder.marketplaceView", props)
        this@marketplaceView.child(MarketplaceViews::class.rClass, props = props) {
        }
    }
