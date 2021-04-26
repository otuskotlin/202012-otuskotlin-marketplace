package ru.otus.otuskotlin.marketplace.backend.repository.sql

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.marketplace.backend.repository.sql.schema.*
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoIndexException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoWrongIdException
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IAdRepository
import java.sql.Connection

abstract class AdRepoSql<T : IMpItemModel, D : AdDto<T>>(
    url: String,
    driver: String,
    user: String,
    password: String,
    val dtoCompanion: UUIDEntityClass<D>,
    val adsTable: AdsTable,
    open val printLogs: Boolean = false,
    initObjects: Collection<T> = emptyList(),
    val toModel: D.() -> T
) : IAdRepository<T> {

    private val db by lazy {
        val _db = Database.connect(
            url = url,
            driver = driver,
            user = user,
            password = password
        )
        transaction {
            SchemaUtils.create(
                DemandsTable,
                ProposalsTable,
                DemandsTagsTable,
            )
        }
        _db
    }

    init {
        runBlocking {
            initObjects.forEach {
                createAd(it, true)
            }
        }
    }

    protected suspend fun listAds(context: MpBeContext, filter: IMpItemFilterModel): Collection<T> {
        return transaction(
            transactionIsolation = Connection.TRANSACTION_SERIALIZABLE,
            repetitionAttempts = 3,
            db = db
        ) {
            if (printLogs) addLogger(StdOutSqlLogger)
            val found =
                if (filter.text.isNotBlank()) {
                    dtoCompanion.find {
                        (adsTable.title like "%${filter.text}%") or (adsTable.description like "%${filter.text}%")
                    }
                } else {
                    dtoCompanion.all()
                }

            context.pageCount = found.count().toInt()
            found
                .limit(filter.count.takeIf { it > 0 } ?: 20, filter.offset.toLong().takeIf { it > 0 } ?: 0)
                .map { it.toModel() }.toList()
        }
    }

    protected suspend fun createAd(model: T, setId: Boolean = false): T {
        return transaction(db) {
            if (printLogs) addLogger(StdOutSqlLogger)
            val adNew = dtoCompanion.new(if (setId) model.id.asUUID() else null) {
                avatar = model.avatar
                title = model.title
                description = model.description
            }
            val adNewId = adNew.id
//            model.tagIds.forEach {
//                DemandTagDto.new {
//                    this.tagId = it
//                    this.demand = adNew
//                }
//            }
            dtoCompanion[adNewId].toModel()
        }
    }

    protected suspend fun readAd(id: IMpItemIdModel): T {
        return transaction {
            if (printLogs) addLogger(StdOutSqlLogger)
            println("ID to read: ${id.asString()}")
            dtoCompanion[id.asUUID()].toModel()
        }
    }

    protected suspend fun updateAd(model: T): T {
        val adId = model.id.asUUID()
        return transaction(db) {
            if (printLogs) addLogger(StdOutSqlLogger)
            val adToUpdate = dtoCompanion[adId]
            adToUpdate
                .apply { of(model) }
                .toModel()
//            DemandsTagsTable.deleteWhere { DemandsTagsTable.demand eq adId }
//            model.tagIds.forEach {
//                DemandTagDto.new {
//                    this.tagId = it
//                    this.demand = adToUpdate
//                }
//            }
            dtoCompanion[adId].toModel()
        }
    }

    protected suspend fun deleteAd(id: IMpItemIdModel): T {
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.asString())
        return transaction(db) {
            if (printLogs) addLogger(StdOutSqlLogger)
            val old = dtoCompanion[id.asUUID()]
//            adsTable.deleteWhere { DemandsTagsTable.demand eq old.id }
            old.delete()
            old.toModel()
        }
    }

    protected suspend fun offersAd(context: MpBeContext): Collection<T> {
        val title = context.requestProposal.title
        if (title.length < 3) throw MpRepoIndexException(title)
        return emptyList()
    }

}
