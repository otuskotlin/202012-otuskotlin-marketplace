package ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.dto

import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey

@DynamoDbBean
data class ProposalDtoDynamoDB(
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
    constructor(id: String, model: MpProposalModel) :
            this(
                id = id.takeIf { it != MpProposalModel.NONE.id.id },
                avatar = model.avatar.takeIf { it != MpProposalModel.NONE.avatar },
                title = model.title.takeIf { it != MpProposalModel.NONE.title },
                description = model.description.takeIf { it != MpProposalModel.NONE.description },
                tagIds = model.tagIds.takeIf { it != MpProposalModel.NONE.tagIds },
                techDets = model.techDets
                    .takeIf { it != MpProposalModel.NONE.techDets }
                    ?.map { TechDetDtoDynamoDB(it) }
            )

    fun toModel(): MpProposalModel =
        MpProposalModel(
            id = id?.let { MpProposalIdModel(it) } ?: MpProposalModel.NONE.id,
            avatar = avatar ?: MpProposalModel.NONE.avatar,
            title = title ?: MpProposalModel.NONE.title,
            description = description ?: MpProposalModel.NONE.description,
            tagIds = tagIds?.toMutableSet() ?: MpProposalModel.NONE.tagIds,
            techDets = techDets?.map { it.toModel() }?.toMutableSet() ?: MpProposalModel.NONE.techDets,
        )

    companion object {
        const val TABLE_NAME = "proposals"
        const val ID = "id"
        const val AVATAR = "avatar"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val TAG_ID_LIST = "tag_ids"
        const val TECH_DETS = "tech_dets"
        const val IDX_BY_TITLE = "idx_by_title"
        val SCHEMA: TableSchema<ProposalDtoDynamoDB> = TableSchema.fromClass(ProposalDtoDynamoDB::class.java)
    }
}