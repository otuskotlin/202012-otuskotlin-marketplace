package ru.otus.otuskotlin.marketplace.backend.repository.dynamodb

import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withTimeout
import ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.dto.ProposalDtoDynamoDB
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoIndexException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoWrongIdException
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IProposalRepository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.Expression
import software.amazon.awssdk.enhanced.dynamodb.internal.AttributeValues
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import java.util.*
import java.util.function.Consumer

class ProposalRepositoryDynamoDB
private constructor(
    private val table: DynamoDbAsyncTable<ProposalDtoDynamoDB>,
    private val timeoutMillis: Long
) : IProposalRepository {
    constructor(client: DynamoDbEnhancedAsyncClient, timeoutMillis: Long = 30_000) :
            this(client.table(ProposalDtoDynamoDB.TABLE_NAME, ProposalDtoDynamoDB.SCHEMA), timeoutMillis)

    constructor(timeoutMillis: Long = 30_000) :
            this(DynamoDbEnhancedAsyncClient.create(), timeoutMillis)

    override suspend fun read(context: MpBeContext): MpProposalModel {
        val id = context.requestProposalId
        if (id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeoutMillis) {
            table.getItem { it.key { it.partitionValue(id.id) } }
                .await()
                .toModel()
                .also { context.responseProposal = it }
        }
    }

    override suspend fun create(context: MpBeContext): MpProposalModel {
        val id = UUID.randomUUID().toString()
        val dto = ProposalDtoDynamoDB(id, context.requestProposal)
        return withTimeout(timeoutMillis) {
            table.updateItem {
                it.item(dto)
                it.conditionExpression(
                    Expression.builder()
                        .expression("attribute_not_exists(${ProposalDtoDynamoDB.ID})")
                        .build()
                )
            }
                .await()
                .toModel()
                .also { context.responseProposal = it }
        }
    }

    override suspend fun update(context: MpBeContext): MpProposalModel {
        val id = context.requestProposalId
        if (id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(id.id)
        val dto = ProposalDtoDynamoDB(id.id, context.requestProposal)
        return withTimeout(timeoutMillis) {
            table.updateItem {
                it.item(dto)
                it.conditionExpression(
                    Expression.builder()
                        .expression("attribute_exists(${ProposalDtoDynamoDB.ID})")
                        .build()
                )
            }
                .await()
                .toModel()
                .also { context.responseProposal = it }
        }
    }

    override suspend fun delete(context: MpBeContext): MpProposalModel {
        val id = context.requestProposalId
        if (id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return withTimeout(timeoutMillis) {
            table.deleteItem { it.key { it.partitionValue(id.id) } }
                .await()
                .toModel()
                .also { context.responseProposal = it }
        }
    }

    override suspend fun list(context: MpBeContext): Collection<MpProposalModel> {
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
                                .putExpressionName("#title", ProposalDtoDynamoDB.TITLE)
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
                    context.responseProposals = it.toMutableList()
                    context.pageCount = if (it.isNotEmpty()) {
                        (totalItemsToFetch + filter.count - 1) / filter.count
                    } else {
                        Int.MIN_VALUE
                    }
                }
        }
    }

    override suspend fun offers(context: MpBeContext): Collection<MpProposalModel> {
        val title = context.requestDemand.title
        return withTimeout(timeoutMillis) {
            table
                .index(ProposalDtoDynamoDB.IDX_BY_TITLE)
                .query(Consumer {
                    it.queryConditional(QueryConditional.keyEqualTo {
                        it.partitionValue(title)
                    })
                })
                .flatMapIterable { it.items() }
                .asFlow()
                .map { it.toModel() }
                .toList()
                .also { context.responseProposals = it.toMutableList() }
        }
    }
}