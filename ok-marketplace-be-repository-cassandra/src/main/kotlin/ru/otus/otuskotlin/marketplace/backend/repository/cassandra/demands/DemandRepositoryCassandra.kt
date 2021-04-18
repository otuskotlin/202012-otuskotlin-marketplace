package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.demands

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.guava.await
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.MpRepositoryCassandra
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.dto.TechDetCassandraDto
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
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

    private val dao by lazy {
        mapper.demandDao(keyspaceName, DemandCassandraDto.DEMANDS_TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout.toMillis()) {
                        createAsync(DemandCassandraDto.Companion.of(model)).await()
                    }
                }
            }
        }
    }

    override suspend fun read(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = dao.readAsync(id.id).await()?.toModel() ?: throw MpRepoNotFoundException(id.id)
            context.responseDemand = model
            model
        }
    }

    override suspend fun create(context: MpBeContext): MpDemandModel {
        val id = UUID.randomUUID().toString()
        val dto = DemandCassandraDto.of(context.requestDemand, id)
        return withTimeout(timeout.toMillis()) {
            dao.createAsync(dto).await()
            val model = dao.readAsync(id).await()?.toModel()?: throw MpRepoNotFoundException(id)
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
            val lockKey = dao.readAsync(id.id).await()?.lockVersion ?: throw MpRepoNotFoundException(id.id)
            val isUpdated = dao.updateAsync(DemandCassandraDto.Companion.of(context.requestDemand), lockKey).await()
            if (!isUpdated) throw MpRepoModifyException(id.id)
            val model = dao.readAsync(id.id).await()?.toModel() ?: throw MpRepoNotFoundException(id.id)
            context.responseDemand = model
            model
        }
    }

    override suspend fun delete(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = dao.readAsync(id.id).await()?.toModel() ?: throw MpRepoNotFoundException(id.id)
            dao.deleteAsync(id.id).await()
            context.responseDemand = model
            model
        }
    }

    override suspend fun list(context: MpBeContext): Collection<MpDemandModel> {
        TODO("Not yet implemented")
    }

    override suspend fun offers(context: MpBeContext): Collection<MpDemandModel> {
        TODO("Not yet implemented")
    }

    override fun CqlSession.createTable() {
        execute(
            SchemaBuilder.createTable(DemandCassandraDto.DEMANDS_TABLE_NAME)
                .withPartitionKey(DemandCassandraDto.ID, DataTypes.TEXT)
                .withColumn(DemandCassandraDto.AVATAR, DataTypes.TEXT)
                .withColumn(DemandCassandraDto.DESCRIPTION, DataTypes.TEXT)
                .withColumn(DemandCassandraDto.TITLE, DataTypes.TEXT)
                .withColumn(DemandCassandraDto.TAG_ID_LIST, DataTypes.setOf(DataTypes.TEXT))
                .withColumn(
                    DemandCassandraDto.TECH_DETS,
                    DataTypes.setOf(SchemaBuilder.udt(TechDetCassandraDto.TYPE_NAME, true))
                )
                .withColumn(DemandCassandraDto.LOCK_VERSION, DataTypes.TEXT)
                .build()
        )
    }

    override fun init() = apply {
        val dao = dao
    }

}
