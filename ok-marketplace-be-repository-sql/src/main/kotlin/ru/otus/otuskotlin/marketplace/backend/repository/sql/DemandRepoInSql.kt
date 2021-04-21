package ru.otus.otuskotlin.marketplace.backend.repository.sql

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.DemandDto
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
    initObjects: Collection<MpDemandModel> = emptyList()
) : IDemandRepository {

    private val db by lazy {
        Database.connect(
            url = url,
            driver = driver,
            user = user,
            password = password
        )
    }

    init {
        runBlocking {
            initObjects.forEach {
                create(
                    MpBeContext(
                        requestDemand = it
                    )
                )
            }
        }
    }

    override suspend fun list(context: MpBeContext): Collection<MpDemandModel> {
        val textFilter = context.demandFilter.text
        if (textFilter.length < 3) throw MpRepoIndexException(textFilter)
        return transaction(db) {
            DemandDto.all().map { it.toModel() }
        }
    }

    override suspend fun create(context: MpBeContext): MpDemandModel {
        val model = context.requestDemand
        return transaction(db) {
            val id = DemandDto.new {
                avatar = model.avatar
            }.id
            DemandDto[id].toModel()
        }
    }

    override suspend fun read(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return transaction {
            context.responseDemand = DemandDto[id.asUUID()]
                .toModel()
            context.requestDemand
        }
    }

    override suspend fun update(context: MpBeContext): MpDemandModel {
        if (context.requestDemand.id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(context.requestDemand.id.id)
        val model = context.requestDemand
        return transaction(db) {
            context.responseDemand = DemandDto[model.id.asUUID()]
                .apply { of(model) }
                .toModel()
            context.requestDemand
        }
    }

    override suspend fun delete(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return transaction(db) {
            val old = DemandDto[id.asUUID()]
            old.delete()
            old.toModel()
        }
    }

    override suspend fun offers(context: MpBeContext): Collection<MpDemandModel> {
        val title = context.requestProposal.title
        if (title.length < 3) throw MpRepoIndexException(title)
        return emptyList()
    }

}
