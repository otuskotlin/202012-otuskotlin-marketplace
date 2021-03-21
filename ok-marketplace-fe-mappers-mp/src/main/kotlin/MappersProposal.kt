import models.ProposalIdModel
import models.ProposalModel
import models.MpFrontContext
import models.TagIdModel
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

fun MpFrontContext.responded(data: MpResponseProposalList) = apply {
    proposals = data.proposals?.map { it.toInternal() }?.toMutableList() ?: mutableListOf()
}

fun MpFrontContext.responded(data: MpResponseProposalCreate) = apply {
    proposal = data.proposal?.toInternal() ?: ProposalModel.NONE
}

fun MpFrontContext.responded(data: MpResponseProposalRead) = apply {
    proposal = data.proposal?.toInternal() ?: ProposalModel.NONE
}

fun MpFrontContext.responded(data: MpResponseProposalUpdate) = apply {
    proposal = data.proposal?.toInternal() ?: ProposalModel.NONE
}

fun MpFrontContext.responded(data: MpResponseProposalDelete) = apply {
    if (data.deleted == true) {
        proposal = ProposalModel.NONE
    }
}

fun MpFrontContext.responded(data: MpResponseProposalOffers) = apply {
    proposalOffers = data.proposalDemands?.map { it.toInternal() }?.toMutableList() ?: mutableListOf()
}

fun MpProposalDto.toInternal() = ProposalModel(
    id = id?.let { ProposalIdModel(it) } ?: ProposalIdModel.NONE,
    avatar = avatar ?: "",
    title = title ?: "",
    description = description ?: "",
    tagIds = tagIds?.map { TagIdModel(it) }?.toMutableSet() ?: mutableSetOf(),
    techDets = techDets?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf(),
)
