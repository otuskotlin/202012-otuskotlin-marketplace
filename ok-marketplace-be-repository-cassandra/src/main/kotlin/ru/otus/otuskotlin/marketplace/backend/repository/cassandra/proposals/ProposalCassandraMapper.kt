package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.proposals

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace
import com.datastax.oss.driver.api.mapper.annotations.DaoTable
import com.datastax.oss.driver.api.mapper.annotations.Mapper

@Mapper
interface ProposalCassandraMapper {

    @DaoFactory
    fun proposalDao(
        @DaoKeyspace keyspace: String,
        @DaoTable table: String
    ): ProposalCassandraDao

    companion object {
        fun builder(session: CqlSession) = ProposalCassandraMapperBuilder(session)
    }
}
