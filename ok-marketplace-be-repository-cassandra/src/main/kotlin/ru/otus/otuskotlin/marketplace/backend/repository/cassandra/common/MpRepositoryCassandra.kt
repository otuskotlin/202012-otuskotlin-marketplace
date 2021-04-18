package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.dto.TechDetCassandraDto
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.dto.TechParamCassandraDto
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.dto.UnitTypeCassandraDto
import java.io.Closeable
import java.net.InetAddress
import java.net.InetSocketAddress
import kotlin.coroutines.CoroutineContext

abstract class MpRepositoryCassandra(
    protected open val keyspaceName: String,
    protected open val hosts: String = "",
    protected open val port: Int = 9042,
    protected open val user: String = "cassandra",
    protected open val pass: String = "cassandra",
    protected open val replicationFactor: Int = 1,
): CoroutineScope, Closeable {
    private val job = Job()

    /**
     * Создание сессии, кейспейса, регистрация типов и создание таблицы
     */
    protected val session by lazy {
        val builder = CqlSession.builder()
            .addContactPoints(parseAddresses(hosts, port))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials(user, pass)
        builder.build().apply {
            createKeyspace() // создание кейспейса
        }
        builder.withKeyspace(keyspaceName).build().apply {
            createTypeProducer() // регистрация udt
            createTable()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun close() {
        job.cancel()
    }

    protected open fun CqlSession.createKeyspace() =
        execute(
            SchemaBuilder.createKeyspace(keyspaceName)
                .ifNotExists()
                .withSimpleStrategy(replicationFactor)
                .build()
        )

    protected open fun CqlSession.createTypeProducer() {
        execute(
            SchemaBuilder.createType(UnitTypeCassandraDto.TYPE_NAME)
                .ifNotExists()
                .withField(UnitTypeCassandraDto.ID, DataTypes.TEXT)
                .withField(UnitTypeCassandraDto.NAME, DataTypes.TEXT)
                .withField(UnitTypeCassandraDto.DESCRIPTION, DataTypes.TEXT)
                .withField(UnitTypeCassandraDto.IS_BASE, DataTypes.BOOLEAN)
                .withField(UnitTypeCassandraDto.SYMBOL, DataTypes.TEXT)
                .withField(UnitTypeCassandraDto.SYMBOLS, DataTypes.setOf(DataTypes.TEXT))
                .withField(UnitTypeCassandraDto.SYNONYMS, DataTypes.setOf(DataTypes.TEXT))
                .build()
        )
        execute(
            SchemaBuilder.createType(TechParamCassandraDto.TYPE_NAME)
                .ifNotExists()
                .withField(TechParamCassandraDto.ID, DataTypes.TEXT)
                .withField(TechParamCassandraDto.NAME, DataTypes.TEXT)
                .withField(TechParamCassandraDto.DESCRIPTION, DataTypes.TEXT)
                .withField(TechParamCassandraDto.PRIORITY, DataTypes.DOUBLE)
                .withField(
                    TechParamCassandraDto.UNITS,
                    DataTypes.setOf(SchemaBuilder.udt(UnitTypeCassandraDto.TYPE_NAME, true))
                )
                .build()
        )
        execute(
            SchemaBuilder.createType(TechDetCassandraDto.TYPE_NAME)
                .ifNotExists()
                .withField(TechDetCassandraDto.ID, DataTypes.TEXT)
                .withField(TechDetCassandraDto.VALUE, DataTypes.TEXT)
                .withField(TechDetCassandraDto.PARAM, SchemaBuilder.udt(TechParamCassandraDto.TYPE_NAME, true))
                .withField(TechDetCassandraDto.COMPARABLE_VALUE, DataTypes.DOUBLE)
                .withField(TechDetCassandraDto.UNIT_TYPE, SchemaBuilder.udt(UnitTypeCassandraDto.TYPE_NAME, true))
                .build()
        )
    }

    private fun parseAddresses(hosts: String, port: Int): Collection<InetSocketAddress> = hosts
        .split(Regex("""\s*,\s*"""))
        .map { InetSocketAddress(InetAddress.getByName(it), port) }


    abstract fun CqlSession.createTable()

    abstract fun init(): MpRepositoryCassandra
}
