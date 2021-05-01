package ru.otus.otuskotlin.marketplace.backend.repository.dynamodb

import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withTimeout
import ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.dto.DemandDtoDynamoDB
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoIndexException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoWrongIdException
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IDemandRepository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.Expression
import software.amazon.awssdk.enhanced.dynamodb.internal.AttributeValues
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import java.util.*
import java.util.function.Consumer

class DemandRepositoryDynamoDB
private constructor(
    private val table: DynamoDbAsyncTable<DemandDtoDynamoDB>,
    private val timeoutMillis: Long
) : IDemandRepository {
    constructor(client: DynamoDbEnhancedAsyncClient, timeoutMillis: Long = 30_000) :
            this(client.table(DemandDtoDynamoDB.TABLE_NAME, DemandDtoDynamoDB.SCHEMA), timeoutMillis)

    constructor(timeoutMillis: Long = 30_000) :
            this(DynamoDbEnhancedAsyncClient.create(), timeoutMillis)

    override suspend fun read(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeoutMillis) {
            table
                .getItem { it.key { it.partitionValue(id.id) } }
                .await()
                .toModel()
                .also { context.responseDemand = it }
        }
    }

    override suspend fun create(context: MpBeContext): MpDemandModel {
        val id = UUID.randomUUID().toString()
        val dto = DemandDtoDynamoDB(id, context.requestDemand)
        return withTimeout(timeoutMillis) {
            table
                .updateItem {
                    it.item(dto)
                    it.conditionExpression(
                        Expression.builder()
                            .expression("attribute_not_exists(#id)")
                            .putExpressionName("#id", DemandDtoDynamoDB.ID)
                            .build()
                    )
                }
                .await()
                .toModel()
                .also { context.responseDemand = it }
        }
    }

    override suspend fun update(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        val dto = DemandDtoDynamoDB(id.id, context.requestDemand)
        return withTimeout(timeoutMillis) {
            table
                .updateItem {
                    it.item(dto)
                    it.conditionExpression(
                        Expression.builder()
                            .expression("attribute_exists(#id)")
                            .putExpressionName("#id", DemandDtoDynamoDB.ID)
                            .build()
                    )
                }
                .await()
                .toModel()
                .also { context.responseDemand = it }
        }
    }

    override suspend fun delete(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeoutMillis) {
            table
                .deleteItem { it.key { it.partitionValue(id.id) } }
                .await()
                .toModel()
                .also { context.responseDemand = it }
        }
    }

    override suspend fun list(context: MpBeContext): Collection<MpDemandModel> {
        val filter = context.demandFilter
        if (filter.text.length < 3) throw MpRepoIndexException(filter.text)
        return withTimeout(timeoutMillis) {
            val totalItemsToFetch = filter.offset + filter.count
            table
                .scan {
                    if (filter.text.isNotBlank()) {
                        it.filterExpression(
                            Expression.builder()
                                .expression("contains(#title, :text)")
                                .putExpressionName("#title", DemandDtoDynamoDB.TITLE)
                                .putExpressionValue(":text", AttributeValues.stringValue(filter.text))
                                .build()
                        )
                    }
                    it.limit(filter.count)
                }
                .flatMapIterable { it.items() }
                .asFlow()
                .drop(filter.offset)
                .take(filter.count)
                .map { it.toModel() }
                .toList()
                .also {
                    context.responseDemands = it.toMutableList()
                    context.pageCount = if (it.isNotEmpty()) {
                        (totalItemsToFetch + filter.count - 1) / filter.count
                    } else {
                        Int.MIN_VALUE
                    }
                }
        }
    }

    override suspend fun offers(context: MpBeContext): Collection<MpDemandModel> {
        val title = context.requestDemand.title
        return withTimeout(timeoutMillis) {
            table
                .index(DemandDtoDynamoDB.IDX_BY_TITLE)
                .query(Consumer {
                    it.queryConditional(QueryConditional.keyEqualTo {
                        it.partitionValue(title)
                    })
                })
                .flatMapIterable { it.items() }
                .asFlow()
                .map { it.toModel() }
                .toList()
                .also { context.responseDemands = it.toMutableList() }
        }
    }
}