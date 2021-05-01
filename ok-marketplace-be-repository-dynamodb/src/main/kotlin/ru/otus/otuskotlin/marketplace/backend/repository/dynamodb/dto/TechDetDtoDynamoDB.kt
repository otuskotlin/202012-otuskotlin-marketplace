package ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.dto

import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechDetIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechDetModel
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean

@DynamoDbBean
data class TechDetDtoDynamoDB(
    @get:DynamoDbAttribute(ID)
    var id: String? = null,
    @get:DynamoDbAttribute(PARAM)
    var param: TechParamDtoDynamoDB? = null,
    @get:DynamoDbAttribute(VALUE)
    var value: String? = null,
    @get:DynamoDbAttribute(UNIT_TYPE)
    var unit: UnitTypeDtoDynamoDB? = null,
    @get:DynamoDbAttribute(COMPARABLE_VALUE)
    var comparableValue: Double? = null
) {

    constructor(model: MpTechDetModel) :
            this(
                id = model.id.takeIf { it != MpTechDetModel.NONE.id }?.id,
                param = model.param.takeIf { it != MpTechDetModel.NONE.param }?.let { TechParamDtoDynamoDB(it) },
                value = model.value.takeIf { it != MpTechDetModel.NONE.value },
                unit = model.unit.takeIf { it != MpTechDetModel.NONE.unit }?.let { UnitTypeDtoDynamoDB(it) },
                comparableValue = model.comparableValue.takeIf { it != MpTechDetModel.NONE.comparableValue }
            )

    fun toModel(): MpTechDetModel =
        MpTechDetModel(
            id = id?.let { MpTechDetIdModel(it) } ?: MpTechDetModel.NONE.id,
            param = param?.toModel() ?: MpTechDetModel.NONE.param,
            value = value ?: MpTechDetModel.NONE.value,
            unit = unit?.toModel() ?: MpTechDetModel.NONE.unit,
            comparableValue = comparableValue ?: MpTechDetModel.NONE.comparableValue,
        )

    companion object {
        const val ID = "id"
        const val PARAM = "param"
        const val VALUE = "value"
        const val UNIT_TYPE = "unit_type"
        const val COMPARABLE_VALUE = "comparable_value"
    }
}