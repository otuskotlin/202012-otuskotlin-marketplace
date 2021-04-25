package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.demands

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace
import com.datastax.oss.driver.api.mapper.annotations.DaoTable
import com.datastax.oss.driver.api.mapper.annotations.Mapper

@Mapper
interface DemandCassandraMapper {

    @DaoFactory
    fun demandByIdDao(
        @DaoKeyspace keyspace: String,
        @DaoTable table: String
    ): DemandByIdCassandraDao

    @DaoFactory
    fun demandByTitleDao(
        @DaoKeyspace keyspace: String,
        @DaoTable table: String
    ): DemandByTitleCassandraDao
}
