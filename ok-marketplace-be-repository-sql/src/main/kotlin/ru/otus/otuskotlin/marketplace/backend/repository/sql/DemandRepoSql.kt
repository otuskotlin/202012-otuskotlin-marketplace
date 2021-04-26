package ru.otus.otuskotlin.marketplace.backend.repository.sql

import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.*
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoIndexException
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel

class DemandRepoSql(
    url: String = "jdbc:postgresql://localhost:5432/marketplace",
    driver: String = "org.postgresql.Driver",
    user: String = "postgres",
    password: String = "postgres",
    printLogs: Boolean = false,
    initObjects: Collection<MpDemandModel> = emptyList()
) : AdRepoSql<MpDemandModel, DemandDto, DemandTagDto>(
    url = url,
    driver = driver,
    user = user,
    password = password,
    dtoCompanion = DemandDto.Companion,
    adsTable = DemandsTable,
    adsTagsTable = DemandsTagsTable,
    adTagCompanion = DemandTagDto,
    printLogs = printLogs,
    toModel = { toModel() },
    initObjects = initObjects
) {

    override suspend fun list(context: MpBeContext): Collection<MpDemandModel> {
        val filter = context.demandFilter
        val demands = listAds(context = context, filter = filter)
        context.responseDemands = demands.toMutableList()
        return demands
    }

    override suspend fun create(context: MpBeContext): MpDemandModel {
        val model = context.requestDemand
        val demand = createAd(model)
        context.responseDemand = demand
        return demand
    }

    override suspend fun read(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        val demand = readAd(id)
        context.responseDemand = demand
        return demand
    }

    override suspend fun update(context: MpBeContext): MpDemandModel {
        val model = context.requestDemand
        val demand = updateAd(model)
        context.responseDemand = demand
        return demand
    }

    override suspend fun delete(context: MpBeContext): MpDemandModel {
        val demandId = context.requestDemandId
        val demand = deleteAd(demandId)
        context.responseDemand = demand
        return demand
    }

    override suspend fun offers(context: MpBeContext): Collection<MpDemandModel> {
        val title = context.requestProposal.title
        if (title.length < 3) throw MpRepoIndexException(title)
        return emptyList()
    }

}
