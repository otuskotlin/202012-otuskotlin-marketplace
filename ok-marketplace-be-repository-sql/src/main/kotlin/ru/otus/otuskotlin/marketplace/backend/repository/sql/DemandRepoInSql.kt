package ru.otus.otuskotlin.marketplace.backend.repository.sql

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.DemandDto
import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.DemandTagDto
import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.DemandsTable
import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.DemandsTagsTable
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoIndexException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoWrongIdException
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IDemandRepository

class DemandRepoInSql(
    url: String = "jdbc:postgresql://localhost:5432/marketplace",
    driver: String = "org.postgresql.Driver",
    user: String = "postgres",
    password: String = "postgres",
    val printLogs: Boolean = false,
    initObjects: Collection<MpDemandModel> = emptyList()
) : IDemandRepository {

    private val db by lazy {
        val _db = Database.connect(
            url = url,
            driver = driver,
            user = user,
            password = password
        )
        transaction { SchemaUtils.create (DemandsTable, DemandsTagsTable) }
        _db
    }

    init {
        runBlocking {
            initObjects.forEach {
                createWithId(MpBeContext(requestDemand = it), true)
            }
        }
    }

    override suspend fun list(context: MpBeContext): Collection<MpDemandModel> {
        val textFilter = context.demandFilter.text
//        if (textFilter.length < 3) throw MpRepoIndexException(textFilter)
        return transaction(db) {
            if(printLogs) addLogger(StdOutSqlLogger)
            context.responseDemands = DemandDto.all().map { it.toModel() }.toMutableList()
            context.responseDemands
        }
    }

    override suspend fun create(context: MpBeContext): MpDemandModel = createWithId(context)

    private suspend fun createWithId(context: MpBeContext, setId: Boolean = false): MpDemandModel {
        val model = context.requestDemand
        return transaction(db) {
            if(printLogs) addLogger(StdOutSqlLogger)
            val demandNew = DemandDto.new(model.id.asUUID().takeIf { setId }) {
                avatar = model.avatar
                title = model.title
                description = model.description
            }
            val demandNewId = demandNew.id
            model.tagIds.forEach {
                DemandTagDto.new {
                    this.tagId = it
                    this.demand = demandNew
                }
            }
            DemandDto[demandNewId].toModel()
        }
    }

    override suspend fun read(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return transaction {
            if(printLogs) addLogger(StdOutSqlLogger)
            context.responseDemand = DemandDto[id.asUUID()].toModel()
            context.responseDemand
        }
    }

    override suspend fun update(context: MpBeContext): MpDemandModel {
        if (context.requestDemand.id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(context.requestDemand.id.id)
        val model = context.requestDemand
        val demandId = model.id.asUUID()
        return transaction(db) {
            if(printLogs) addLogger(StdOutSqlLogger)
            val demandToUpdate = DemandDto[demandId]
            demandToUpdate
                .apply { of(model) }
                .toModel()
            DemandsTagsTable.deleteWhere { DemandsTagsTable.demand eq demandId }
            model.tagIds.forEach {
                DemandTagDto.new {
                    this.tagId = it
                    this.demand = demandToUpdate
                }
            }
            context.responseDemand = DemandDto[demandId].toModel()
            context.responseDemand
        }
    }

    override suspend fun delete(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return transaction(db) {
            if(printLogs) addLogger(StdOutSqlLogger)
            val old = DemandDto[id.asUUID()]
            old.delete()
            context.responseDemand = old.toModel()
            context.responseDemand
        }
    }

    override suspend fun offers(context: MpBeContext): Collection<MpDemandModel> {
        val title = context.requestProposal.title
        if (title.length < 3) throw MpRepoIndexException(title)
        return emptyList()
    }

}
