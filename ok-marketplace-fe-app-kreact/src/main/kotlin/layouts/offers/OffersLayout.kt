package layouts.offers

import com.ccfraser.muirwik.components.table.*
import react.RBuilder
import react.RComponent
import react.RState
import react.rClass

class OffersLayout(props: OffersProps) : RComponent<OffersProps, RState>() {
    override fun RBuilder.render() {

        val offers = props.items

        // Вычисляем свойства, которые будем отображать в таблице
        val techProps = props
            .items
            .asSequence()
            .flatMap { it.techDets }
            .map { it.param }
            .distinctBy { it.id }
            .sortedBy { it.priority }
            .take(5)
            .toList()
        val paramsIds = techProps.map { it.id }.toList()

        // Сортируем свойства для отображения в таблице
        val offersTechDets = offers
            .map { offer ->
                offer
                    .techDets
                    .asSequence()
                    .map { techParam -> techParam.param.id to techParam }
                    .filter { it.first in paramsIds }
                    .toMap()
            }

        mTableContainer {
            mTable {
                mTableHead {
                    mTableRow {
                        mTableCell(variant = MTableCellVariant.head) { +"#" }
                        mTableCell(variant = MTableCellVariant.head) { +"Name" }
                        techProps.forEach {
                            mTableCell(variant = MTableCellVariant.head, align = MTableCellAlign.center) { +it.name }
                        }
                    }
                }
                mTableBody {
                    offers.forEachIndexed { offereIdx, offer ->
                        mTableRow {
                            mTableCell { +offereIdx.toString() }
                            mTableCell { +offer.title }
                            paramsIds.forEach { paramId ->
                                val techDet = offersTechDets[offereIdx][paramId]
                                val techDetUnit = techDet?.unit?.symbol

                                val value = techDet?.value?.let {
                                    if (techDetUnit == null) it else "$it $techDetUnit"
                                }
                                mTableCell(align = MTableCellAlign.center) { +(value ?: "-") }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.offersLayout(props: OffersProps) = child(OffersLayout::class.rClass, props = props) {

}
