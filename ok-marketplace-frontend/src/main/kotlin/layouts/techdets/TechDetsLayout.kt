package layouts.techdets

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.table.*
import layouts.techdets.techdet.TechDetProps
import layouts.techdets.techdet.techDetLayout
import react.RBuilder
import react.RComponent
import react.RState
import react.rClass

class TechDetsLayout(props: TechDetsProps): RComponent<TechDetsProps, RState>() {

    fun RBuilder.techDetColumn(techDetBuilder: RBuilder.() -> Unit) {
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
                        techDetBuilder()
                    }
                }
            }
        }
    }

    override fun RBuilder.render() {
        val techDets = props.techDets
        if (techDets.isNullOrEmpty()) return
        val colNumber = (techDets.size / MIN_ITEMS_PER_TABLE).takeIf { it < 4 } ?: 4
        val columnedTechDets = listOf(
            techDets
        )

        mGridContainer(
            spacing = MGridSpacing.spacing2,
            alignItems = MGridAlignItems.flexStart,
            alignContent = MGridAlignContent.stretch,
            justify = MGridJustify.flexStart
        ) {

            columnedTechDets.forEach { techDetItems ->
                techDetColumn {
                    techDetItems.forEach {
                        techDetLayout(TechDetProps(techDet = it))
                    }
                }
            }
        }
    }

    companion object {
        const val MIN_ITEMS_PER_TABLE = 10
    }
}


fun RBuilder.techDetsLayout(props: TechDetsProps) = child(TechDetsLayout::class.rClass, props = props) {

}
