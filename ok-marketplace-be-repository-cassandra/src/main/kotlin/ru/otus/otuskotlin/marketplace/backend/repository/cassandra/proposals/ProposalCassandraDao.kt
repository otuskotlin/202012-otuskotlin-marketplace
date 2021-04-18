package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.proposals

import com.datastax.oss.driver.api.mapper.annotations.*
import com.google.common.util.concurrent.ListenableFuture

@Dao
interface ProposalCassandraDao {
    @Insert
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun createAsync(dto: ProposalCassandraDto): ListenableFuture<Unit>

    @Select
    fun readAsync(id: String): ListenableFuture<ProposalCassandraDto>

    /**
     *  В данном случае условие в Update избыточно, так как обновляется вся модель.
     *  Может быть нужно при обновлении отдельных полей
     */
    @Update(customIfClause = "${ProposalCassandraDto.LOCK_VERSION} = :lock_key")
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun updateAsync(dto: ProposalCassandraDto, @CqlName("lock_key") lockKey: String): ListenableFuture<Boolean>

    /**
     *  При удалении по ключу требуется указание [entityClass], при удалении по всей модели
     *  класс не требуется указывать, он берется из модели
     */
    @Delete(ifExists = true, entityClass = [ProposalCassandraDto::class])
    fun deleteAsync(id: String): ListenableFuture<Boolean>
}
