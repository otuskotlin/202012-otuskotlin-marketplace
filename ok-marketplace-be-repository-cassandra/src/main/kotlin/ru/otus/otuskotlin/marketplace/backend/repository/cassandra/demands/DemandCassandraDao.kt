package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.demands

import com.datastax.oss.driver.api.mapper.annotations.*
import com.google.common.util.concurrent.ListenableFuture

@Dao
interface DemandCassandraDao {
    @Insert
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun createAsync(dto: DemandCassandraDto): ListenableFuture<Unit>

    @Select
    fun readAsync(id: String): ListenableFuture<DemandCassandraDto>

    /**
     *  В данном случае условие в Update избыточно, так как обновляется вся модель.
     *  Может быть нужно при обновлении отдельных полей
     */
    @Update(customIfClause = "${DemandCassandraDto.LOCK_VERSION} = :lock_key")
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun updateAsync(dto: DemandCassandraDto, @CqlName("lock_key") lockKey: String): ListenableFuture<Boolean>

    @Delete(ifExists = true, entityClass = [DemandCassandraDto::class])
    fun deleteAsync(id: String): ListenableFuture<Boolean>
}
