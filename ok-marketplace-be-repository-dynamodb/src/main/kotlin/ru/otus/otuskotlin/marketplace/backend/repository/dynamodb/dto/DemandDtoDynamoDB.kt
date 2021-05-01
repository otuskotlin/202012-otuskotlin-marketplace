package ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.dto

import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey

@DynamoDbBean
data class DemandDtoDynamoDB(
    @get:DynamoDbPartitionKey
    @get:DynamoDbSecondarySortKey(indexNames = [IDX_BY_TITLE])
    @get:DynamoDbAttribute(ID)
    var id: String? = null,
    @get:DynamoDbAttribute(AVATAR)
    var avatar: String? = null,
    @get:DynamoDbSecondaryPartitionKey(indexNames = [IDX_BY_TITLE])
    @get:DynamoDbAttribute(TITLE)
    var title: String? = null,
    @get:DynamoDbAttribute(DESCRIPTION)
    var description: String? = null,
    @get:DynamoDbAttribute(TAG_ID_LIST)
    var tagIds: Set<String>? = null,
    @get:DynamoDbAttribute(TECH_DETS)
    var techDets: List<TechDetDtoDynamoDB>? = null
) {

    constructor(id: String, model: MpDemandModel) :
            this(
                id = id.takeIf { it != MpDemandModel.NONE.id.id },
                avatar = model.avatar.takeIf { it != MpDemandModel.NONE.avatar },
                title = model.title.takeIf { it != MpDemandModel.NONE.title },
                description = model.description.takeIf { it != MpDemandModel.NONE.description },
                tagIds = model.tagIds.takeIf { it != MpDemandModel.NONE.tagIds },
                techDets = model.techDets
                    .takeIf { it != MpDemandModel.NONE.techDets }
                    ?.map { TechDetDtoDynamoDB(it) }
            )

    fun toModel(): MpDemandModel =
        MpDemandModel(
            id = id?.let { MpDemandIdModel(it) } ?: MpDemandModel.NONE.id,
            avatar = avatar ?: MpDemandModel.NONE.avatar,
            title = title ?: MpDemandModel.NONE.title,
            description = description ?: MpDemandModel.NONE.description,
            tagIds = tagIds?.toMutableSet() ?: MpDemandModel.NONE.tagIds,
            techDets = techDets?.map { it.toModel() }?.toMutableSet() ?: MpDemandModel.NONE.techDets,
        )

    companion object {
        const val TABLE_NAME = "demands"
        const val ID = "id"
        const val AVATAR = "avatar"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val TAG_ID_LIST = "tag_ids"
        const val TECH_DETS = "tech_dets"
        const val IDX_BY_TITLE = "idx_by_title"
        val SCHEMA: TableSchema<DemandDtoDynamoDB> = TableSchema.fromClass(DemandDtoDynamoDB::class.java)
    }
}