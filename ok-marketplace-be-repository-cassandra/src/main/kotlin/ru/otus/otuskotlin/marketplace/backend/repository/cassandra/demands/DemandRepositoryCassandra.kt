package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.demands

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder
import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.guava.await
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.MpRepositoryCassandra
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.dto.TechDetCassandraDto
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoIndexException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoModifyException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoNotFoundException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoWrongIdException
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IDemandRepository
import java.time.Duration
import java.util.*

class DemandRepositoryCassandra(
    override val keyspaceName: String,
    override val hosts: String = "",
    override val port: Int = 9042,
    override val user: String = "cassandra",
    override val pass: String = "cassandra",
    override val replicationFactor: Int = 1,
    private val timeout: Duration = Duration.ofSeconds(30),
    initObjects: Collection<MpDemandModel> = emptyList(),
): IDemandRepository, MpRepositoryCassandra(keyspaceName, hosts, port, user, pass, replicationFactor) {

    private val mapper by lazy {
        DemandCassandraMapperBuilder(session).build()
    }

    /**
     *  DAO для операций по id
     */
    private val daoById by lazy {
        mapper.demandByIdDao(keyspaceName, DemandByIdCassandraDto.DEMANDS_TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout.toMillis()) {
                        createAsync(DemandByIdCassandraDto.of(model)).await()
                    }
                }
            }
        }
    }

    /**
     *  DAO для операций с названиями
     */
    private val daoByTitle by lazy {
        mapper.demandByTitleDao(keyspaceName, DemandByTitleCassandraDto.DEMANDS_TITLE_TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout.toMillis()) {
                        createAsync(DemandByTitleCassandraDto.of(model)).await()
                    }
                }
            }
        }
    }

    override suspend fun read(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw MpRepoNotFoundException(id.id)
            context.responseDemand = model
            model
        }
    }

    /**
     * Запись происходит во все таблицы
     */
    override suspend fun create(context: MpBeContext): MpDemandModel {
        val id = UUID.randomUUID().toString()
        val dtoById = DemandByIdCassandraDto.of(context.requestDemand, id)
        val dtoByTitle = DemandByTitleCassandraDto.of(context.requestDemand, id)
        return withTimeout(timeout.toMillis()) {
            daoById.createAsync(dtoById).await()
            daoByTitle.createAsync(dtoByTitle).await()
            val model = daoById.readAsync(id).await()?.toModel()?: throw MpRepoNotFoundException(id)
            context.responseDemand = model
            model
        }
    }

    /**
     *  Использование Optimistic Lock для примера, в данном случае update аналогичен create
     */
    override suspend fun update(context: MpBeContext): MpDemandModel {
        val id = context.requestDemand.id
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val lockKey = daoById.readAsync(id.id).await()?.lockVersion ?: throw MpRepoNotFoundException(id.id)
            val dtoById = DemandByIdCassandraDto.of(context.requestDemand)
            val dtoByTitle = DemandByTitleCassandraDto.of(context.requestDemand)
            val isUpdated = daoById.updateAsync(dtoById, lockKey).await()
            if (!isUpdated) throw MpRepoModifyException(id.id)
            daoByTitle.createAsync(dtoByTitle).await()
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw MpRepoNotFoundException(id.id)
            context.responseDemand = model
            model
        }
    }

    /**
     * Удаление записи из всех таблиц
     */
    override suspend fun delete(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw MpRepoNotFoundException(id.id)
            daoById.deleteAsync(id.id).await()
            daoByTitle.deleteAsync(DemandByTitleCassandraDto.of(model)).await()
            context.responseDemand = model
            model
        }
    }

    override suspend fun list(context: MpBeContext): Collection<MpDemandModel> {
        val filter = context.demandFilter
        val minCount = filter.offset + filter.count
        if (filter.text.length < 3) throw MpRepoIndexException(filter.text)
         return withTimeout(timeout.toMillis()) {
            val records = daoByTitle.filterByTitleAsync("%${filter.text}%").await().toList()
             if (records.count() < minCount) throw MpRepoIndexException(filter.text)
             val list = flow {
                 for (it in filter.offset until minCount) {
                     emit(
                         daoById.readAsync(records[it]).await()?.toModel()
                             ?: throw MpRepoNotFoundException(records[it])
                     )
                 }
             }.toList()
             context.responseDemands = list.toMutableList()
             context.pageCount = list.count().takeIf { it != 0 }
                 ?.let { (records.count().toDouble() / it + 0.5).toInt() }
                 ?: Int.MIN_VALUE
             list
        }

    }

    override suspend fun offers(context: MpBeContext): Collection<MpDemandModel> {
        val filter = context.requestProposal.title
        if (filter.length < 3) throw MpRepoIndexException(filter)
        return withTimeout(timeout.toMillis()) {
            val records = daoByTitle.filterByTitleAsync("%${filter}%").await().toList()
            val list = flow {
                records.forEach {
                    emit(
                        daoById.readAsync(it).await()?.toModel()
                            ?: throw MpRepoNotFoundException(it)
                    )
                }
            }.toList()
            context.responseDemands = list.toMutableList()
            list
        }
    }

    override fun CqlSession.createTables() {
        execute(
            SchemaBuilder.createTable(DemandByTitleCassandraDto.DEMANDS_TITLE_TABLE_NAME)
                .ifNotExists()
                .withPartitionKey(DemandByTitleCassandraDto.TITLE, DataTypes.TEXT)
                .withClusteringColumn(DemandByTitleCassandraDto.ID, DataTypes.TEXT)
                .withClusteringColumn(DemandByTitleCassandraDto.TIMESTAMP, DataTypes.TIMESTAMP)
//                .withColumn(DemandByTitleCassandraDto.AVATAR, DataTypes.TEXT)
//                .withColumn(DemandByTitleCassandraDto.DESCRIPTION, DataTypes.TEXT)
//                .withColumn(DemandByTitleCassandraDto.TAG_ID_LIST, DataTypes.setOf(DataTypes.TEXT))
//                .withColumn(
//                    DemandByTitleCassandraDto.TECH_DETS,
//                    DataTypes.setOf(SchemaBuilder.udt(TechDetCassandraDto.TYPE_NAME, true))
//                )
                .withClusteringOrder(DemandByTitleCassandraDto.TIMESTAMP, ClusteringOrder.DESC)
                .build()
        )
        execute(
            SchemaBuilder.createTable(DemandByIdCassandraDto.DEMANDS_TABLE_NAME)
                .ifNotExists()
                .withPartitionKey(DemandByIdCassandraDto.ID, DataTypes.TEXT)
                .withColumn(DemandByIdCassandraDto.AVATAR, DataTypes.TEXT)
                .withColumn(DemandByIdCassandraDto.DESCRIPTION, DataTypes.TEXT)
                .withColumn(DemandByIdCassandraDto.TITLE, DataTypes.TEXT)
                .withColumn(DemandByIdCassandraDto.TAG_ID_LIST, DataTypes.setOf(DataTypes.TEXT))
                .withColumn(
                    DemandByIdCassandraDto.TECH_DETS,
                    DataTypes.setOf(SchemaBuilder.udt(TechDetCassandraDto.TYPE_NAME, true))
                )
                .withColumn(DemandByIdCassandraDto.LOCK_VERSION, DataTypes.TEXT)
                .build()
        )
    }

    override fun init() = apply {
        val dao1 = daoById
        val dao2 = daoByTitle
    }

}
