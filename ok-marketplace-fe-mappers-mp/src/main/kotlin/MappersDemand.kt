import models.DemandIdModel
import models.DemandModel
import models.MpFrontContext
import models.TagIdModel
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

fun MpFrontContext.responded(data: MpResponseDemandList) = apply {
    demands = data.demands?.map { it.toInternal() }?.toMutableList() ?: mutableListOf()
}

fun MpFrontContext.responded(data: MpResponseDemandCreate) = apply {
    demand = data.demand?.toInternal() ?: DemandModel.NONE
}

fun MpFrontContext.responded(data: MpResponseDemandRead) = apply {
    demand = data.demand?.toInternal() ?: DemandModel.NONE
}

fun MpFrontContext.responded(data: MpResponseDemandUpdate) = apply {
    demand = data.demand?.toInternal() ?: DemandModel.NONE
}

fun MpFrontContext.responded(data: MpResponseDemandDelete) = apply {
    if (data.deleted == true) {
        demand = DemandModel.NONE
    }
}

fun MpFrontContext.responded(data: MpResponseDemandOffers) = apply {
    demandOffers = data.demandProposals?.map { it.toInternal() }?.toMutableList() ?: mutableListOf()
}

fun MpDemandDto.toInternal() = DemandModel(
    id = id?.let { DemandIdModel(it) } ?: DemandIdModel.NONE,
    avatar = avatar ?: "",
    title = title ?: "",
    description = description ?: "",
    tagIds = tagIds?.map { TagIdModel(it) }?.toMutableSet() ?: mutableSetOf(),
    techDets = techDets?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf(),
)
