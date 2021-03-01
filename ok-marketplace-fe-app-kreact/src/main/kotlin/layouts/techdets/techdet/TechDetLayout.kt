package layouts.techdets.techdet

import com.ccfraser.muirwik.components.table.MTableCellAlign
import com.ccfraser.muirwik.components.table.mTableCell
import com.ccfraser.muirwik.components.table.mTableRow
import react.RBuilder
import react.RComponent
import react.RState
import react.rClass

class TechDetLayout(props: TechDetProps): RComponent<TechDetProps, RState>() {
    override fun RBuilder.render() {
        val techDet = props.techDet
        mTableRow {
            mTableCell { +techDet.param.name }
            mTableCell(align = MTableCellAlign.center) { +"${techDet.value} ${techDet.unit.symbol}" }
        }
    }
}

fun RBuilder.techDetLayout(props: TechDetProps) = child(TechDetLayout::class.rClass, props = props) {

}
