package app.page

import com.ccfraser.muirwik.components.*
import demands.marketplaceDemands
import header.marketplaceHeader
import kotlinx.css.*
import proposals.marketplaceProposals
import react.*
import styled.css

class AppPage(props: AppPageProps) : RComponent<AppPageProps, AppPageState>(props) {
    override fun RBuilder.render() {
        mGridContainer {
            attrs {
                spacing = MGridSpacing.spacing0
                alignItems = MGridAlignItems.stretch
                justify = MGridJustify.flexStart
                direction = MGridDirection.column
            }
            mGridItem(xs = MGridSize.cells12) {
                marketplaceHeader()
            }
            mGridItem {
                css {
                    margin(all = 0.px)
                    padding(all = 0.px)
                    width = 100.vw
//                    height = 100.pc
                    overflow = Overflow.hidden
                }

                mGridContainer {
                    attrs {
                        alignItems = MGridAlignItems.stretch
                        spacing = MGridSpacing.spacing0
                    }
                    mGridItem() {
                        attrs {
                            xs = MGridSize.cells12
                            sm = MGridSize.cells12
                            md = MGridSize.cells5
                            lg = MGridSize.cells3
                        }
                        marketplaceDemands()
                    }

                    mGridItem() {
                        attrs {
                            xs = MGridSize.cells12
                            sm = MGridSize.cells12
                            md = MGridSize.cells7
                            lg = MGridSize.cells9
                        }
                        marketplaceProposals()
                    }
                }
            }
        }
    }

}

fun RBuilder.appPage(props: AppPageProps = AppPageProps()) = child(AppPage::class.rClass, props = props) {}
