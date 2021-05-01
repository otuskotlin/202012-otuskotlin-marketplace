package ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.dto

import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechParamIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechParamModel
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean

@DynamoDbBean
data class TechParamDtoDynamoDB(
    @get:DynamoDbAttribute(ID)
    val id: String? = null,
    @get:DynamoDbAttribute(NAME)
    val name: String? = null,
    @get:DynamoDbAttribute(DESCRIPTION)
    val description: String? = null,
    @get:DynamoDbAttribute(PRIORITY)
    val priority: Double? = null,
    @get:DynamoDbAttribute(UNITS)
    val units: List<UnitTypeDtoDynamoDB>? = null
) {
    constructor(model: MpTechParamModel) :
            this(
                id = model.id.takeIf { it != MpTechParamModel.NONE.id }?.id,
                name = model.name.takeIf { it != MpTechParamModel.NONE.name },
                description = model.description.takeIf { it != MpTechParamModel.NONE.description },
                priority = model.priority.takeIf { it != MpTechParamModel.NONE.priority },
                units = model.units
                    .takeIf { it != MpTechParamModel.NONE.units }
                    ?.map { UnitTypeDtoDynamoDB(it) }
            )

    fun toModel(): MpTechParamModel =
        MpTechParamModel(
            id = id?.let { MpTechParamIdModel(it) } ?: MpTechParamIdModel.NONE,
            name = name ?: MpTechParamModel.NONE.name,
            description = description ?: MpTechParamModel.NONE.description,
            priority = priority ?: MpTechParamModel.NONE.priority,
            units = units?.map { it.toModel() }?.toMutableSet() ?: MpTechParamModel.NONE.units
        )

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val PRIORITY = "priority"
        const val UNITS = "units"
    }
}