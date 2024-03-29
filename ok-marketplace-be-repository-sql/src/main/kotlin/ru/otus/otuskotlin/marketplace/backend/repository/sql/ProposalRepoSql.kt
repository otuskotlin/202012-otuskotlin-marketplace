package ru.otus.otuskotlin.marketplace.backend.repository.sql

import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.ProposalDto
import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.ProposalTagDto
import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.ProposalsTable
import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.ProposalsTagsTable
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoIndexException
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IProposalRepository

class ProposalRepoSql(
    url: String = "jdbc:postgresql://localhost:5432/marketplace",
    driver: String = "org.postgresql.Driver",
    user: String = "postgres",
    password: String = "postgres",
    printLogs: Boolean = false,
    initObjects: Collection<MpProposalModel> = emptyList()
) : IProposalRepository, AdRepoSql<MpProposalModel, ProposalDto, ProposalTagDto>(
    url = url,
    driver = driver,
    user = user,
    password = password,
    dtoCompanion = ProposalDto.Companion,
    adsTable = ProposalsTable,
    adsTagsTable = ProposalsTagsTable,
    adTagCompanion = ProposalTagDto,
    printLogs = printLogs,
    toModel = { toModel() },
    initObjects = initObjects
) {

    override suspend fun list(context: MpBeContext): Collection<MpProposalModel> {
        val filter = context.proposalFilter
        val proposals = listAds(context = context, filter = filter)
        context.responseProposals = proposals.toMutableList()
        return proposals
    }

    override suspend fun create(context: MpBeContext): MpProposalModel {
        val model = context.requestProposal
        val proposal = createAd(model)
        context.responseProposal = proposal
        return proposal
    }

    override suspend fun read(context: MpBeContext): MpProposalModel {
        val id = context.requestProposalId
        val proposal = readAd(id)
        context.responseProposal = proposal
        return proposal
    }

    override suspend fun update(context: MpBeContext): MpProposalModel {
        val model = context.requestProposal
        val proposal = updateAd(model)
        context.responseProposal = proposal
        return proposal
    }

    override suspend fun delete(context: MpBeContext): MpProposalModel {
        val proposalId = context.requestProposalId
        val proposal = deleteAd(proposalId)
        context.responseProposal = proposal
        return proposal
    }

    override suspend fun offers(context: MpBeContext): Collection<MpProposalModel> {
        val title = context.requestProposal.title
        if (title.length < 3) throw MpRepoIndexException(title)
        return emptyList()
    }

}
