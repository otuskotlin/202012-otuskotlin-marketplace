package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.proposals

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
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IProposalRepository
import java.time.Duration
import java.util.*

class ProposalRepositoryCassandra(
    override val keyspaceName: String,
    override val hosts: String = "",
    override val port: Int = 9042,
    override val user: String = "cassandra",
    override val pass: String = "cassandra",
    override val replicationFactor: Int = 1,
    private val timeout: Duration = Duration.ofSeconds(30),
    initObjects: Collection<MpProposalModel> = emptyList(),
): IProposalRepository, MpRepositoryCassandra(keyspaceName, hosts, port, user, pass, replicationFactor) {

    private val mapper by lazy {
        ProposalCassandraMapper.builder(session).build()
    }

    /**
     *  DAO для операций по id
     */
    private val dao by lazy {
        mapper.proposalDao(keyspaceName, ProposalCassandraDto.PROPOSALS_TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout.toMillis()) {
                        createAsync(ProposalCassandraDto.of(model)).await()
                    }
                }
            }
        }
    }

    override suspend fun read(context: MpBeContext): MpProposalModel {
        val id = context.requestProposalId
        if (id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = dao.readAsync(id.id).await()?.toModel() ?: throw MpRepoNotFoundException(id.id)
            context.responseProposal = model
            model
        }
    }

    /**
     * Запись происходит во все таблицы
     */
    override suspend fun create(context: MpBeContext): MpProposalModel {
        val id = UUID.randomUUID().toString()
        val dto = ProposalCassandraDto.of(context.requestProposal, id)
        return withTimeout(timeout.toMillis()) {
            dao.createAsync(dto).await()
            val model = dao.readAsync(id).await()?.toModel()?: throw MpRepoNotFoundException(id)
            context.responseProposal = model
            model
        }
    }

    /**
     *  Использование Optimistic Lock для примера, в данном случае update аналогичен create
     */
    override suspend fun update(context: MpBeContext): MpProposalModel {
        val id = context.requestProposal.id
        if (id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val lockKey = dao.readAsync(id.id).await()?.lockVersion ?: throw MpRepoNotFoundException(id.id)
            val dto = ProposalCassandraDto.of(context.requestProposal)
            val isUpdated = dao.updateAsync(dto, lockKey).await()
            if (!isUpdated) throw MpRepoModifyException(id.id)
            val model = dao.readAsync(id.id).await()?.toModel() ?: throw MpRepoNotFoundException(id.id)
            context.responseProposal = model
            model
        }
    }

    /**
     * Удаление записи из всех таблиц
     */
    override suspend fun delete(context: MpBeContext): MpProposalModel {
        val id = context.requestProposalId
        if (id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = dao.readAsync(id.id).await()?.toModel() ?: throw MpRepoNotFoundException(id.id)
            dao.deleteAsync(id.id).await()
            context.responseProposal = model
            model
        }
    }

    override suspend fun list(context: MpBeContext): Collection<MpProposalModel> {
        val filter = context.proposalFilter
        var lastIndex = filter.offset + filter.count
        if (filter.text.length < 3) throw MpRepoIndexException(filter.text)
         return withTimeout(timeout.toMillis()) {
            val records = dao.filterByTitleAsync("%${filter.text}%").await().toList()
                .sortedByDescending { it.timestamp }
             val recordsCount = records.count()
             if (recordsCount < lastIndex) lastIndex = recordsCount
             val list = records.subList(filter.offset, lastIndex).map { it.toModel() }
             context.responseProposals = list.toMutableList()
             context.pageCount = list.count().takeIf { it != 0 }
                 ?.let { (recordsCount.toDouble() / it + 0.5).toInt() }
                 ?: Int.MIN_VALUE
             list
        }

    }

    override suspend fun offers(context: MpBeContext): Collection<MpProposalModel> {
        val filter = context.requestDemand.title
        if (filter.length < 3) throw MpRepoIndexException(filter)
        return withTimeout(timeout.toMillis()) {
            val list = dao.filterByTitleAsync("%${filter}%").await().map { it.toModel() }
            context.responseProposals = list.toMutableList()
            list
        }
    }

    override fun CqlSession.createTables() {
        execute(
            SchemaBuilder.createTable(ProposalCassandraDto.PROPOSALS_TABLE_NAME)
                .ifNotExists()
                .withPartitionKey(ProposalCassandraDto.ID, DataTypes.TEXT)
                .withClusteringColumn(ProposalCassandraDto.TIMESTAMP, DataTypes.TIMESTAMP)
                .withClusteringColumn(ProposalCassandraDto.TITLE, DataTypes.TEXT)
                .withColumn(ProposalCassandraDto.AVATAR, DataTypes.TEXT)
                .withColumn(ProposalCassandraDto.DESCRIPTION, DataTypes.TEXT)
                .withColumn(ProposalCassandraDto.TAG_ID_LIST, DataTypes.setOf(DataTypes.TEXT))
                .withColumn(
                    ProposalCassandraDto.TECH_DETS,
                    DataTypes.setOf(SchemaBuilder.udt(TechDetCassandraDto.TYPE_NAME, true))
                )
                .withColumn(ProposalCassandraDto.LOCK_VERSION, DataTypes.TEXT)
                .withClusteringOrder(ProposalCassandraDto.TIMESTAMP, ClusteringOrder.DESC)
                .withClusteringOrder(ProposalCassandraDto.TITLE, ClusteringOrder.DESC)
                .build()
        )
    }

    override fun CqlSession.createIndexes() {
        execute(
            SchemaBuilder.createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(ProposalCassandraDto.PROPOSALS_TABLE_NAME)
                .andColumn(ProposalCassandraDto.TITLE)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to "en"))
                .build()
        )
    }

    override fun init() = apply {
        val dao = dao
    }

}
