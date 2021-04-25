package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.demands

import com.datastax.oss.driver.api.mapper.annotations.*
import com.google.common.util.concurrent.ListenableFuture

@Dao
interface DemandByIdCassandraDao {
    @Insert
    @StatementAttributes(consistencyLevel = "ONE")
    fun createAsync(dto: DemandByIdCassandraDto): ListenableFuture<Unit>

    @Select
    fun readAsync(id: String): ListenableFuture<DemandByIdCassandraDto?>

    /**
     *  В данном случае условие в Update избыточно, так как обновляется вся модель.
     *  Может быть нужно при обновлении отдельных полей
     */
    @Update(customIfClause = "${DemandByIdCassandraDto.LOCK_VERSION} = :lock_key")
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun updateAsync(dto: DemandByIdCassandraDto, @CqlName("lock_key") lockKey: String): ListenableFuture<Boolean>

    /**
     *  При удалении по ключу требуется указание [entityClass], при удалении по всей модели
     *  класс не требуется указывать, он берется из модели
     */
    @Delete(ifExists = true, entityClass = [DemandByIdCassandraDto::class])
    fun deleteAsync(id: String): ListenableFuture<Boolean>
}

@Dao
interface DemandByTitleCassandraDao {
    @Insert
    @StatementAttributes(consistencyLevel = "ONE")
    fun createAsync(dto: DemandByTitleCassandraDto): ListenableFuture<Unit>

    @Select(
        customWhereClause = "${DemandByTitleCassandraDto.TITLE_INDEX} LIKE :filter",
    )
    fun filterByTitleAsync(filter: String): ListenableFuture<Collection<DemandByTitleCassandraDto>>
//
//    @Query("SELECT ${DemandByTitleCassandraDto.ID} FROM ${DemandByTitleCassandraDto.DEMANDS_TITLE_TABLE_NAME}" +
//            "WHERE ${DemandByTitleCassandraDto.TITLE} LIKE :filter ORDER BY ${DemandByTitleCassandraDto.TIMESTAMP} DESC")
//    fun filterByTitleAsync(filter: String): ListenableFuture<Collection<String>>

    @Delete
    fun deleteAsync(dto: DemandByTitleCassandraDto): ListenableFuture<Unit>
}
