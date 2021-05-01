package ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.dto

import ru.otus.otuskotlin.marketplace.common.backend.models.MpUnitTypeIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpUnitTypeModel
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean

@DynamoDbBean
data class UnitTypeDtoDynamoDB(
    @get:DynamoDbAttribute(ID)
    val id: String? = null,
    @get:DynamoDbAttribute(NAME)
    val name: String? = null,
    @get:DynamoDbAttribute(DESCRIPTION)
    val description: String? = null,
    @get:DynamoDbAttribute(SYNONYMS)
    val synonyms: Set<String>? = null,
    @get:DynamoDbAttribute(SYMBOL)
    val symbol: String? = null,
    @get:DynamoDbAttribute(SYMBOLS)
    val symbols: Set<String>? = null,
    @get:DynamoDbAttribute(IS_BASE)
    val isBase: Boolean? = null
) {
    constructor(model: MpUnitTypeModel) :
            this(
                id = model.id.takeIf { it != MpUnitTypeModel.NONE.id }?.id,
                name = model.name.takeIf { it != MpUnitTypeModel.NONE.name },
                description = model.description.takeIf { it != MpUnitTypeModel.NONE.description },
                synonyms = model.synonyms.takeIf { it != MpUnitTypeModel.NONE.synonyms },
                symbol = model.symbol.takeIf { it != MpUnitTypeModel.NONE.symbol },
                symbols = model.symbols.takeIf { it != MpUnitTypeModel.NONE.symbols },
                isBase = model.isBase.takeIf { it != MpUnitTypeModel.NONE.isBase }
            )

    fun toModel(): MpUnitTypeModel =
        MpUnitTypeModel(
            id = id?.let { MpUnitTypeIdModel(it) } ?: MpUnitTypeIdModel.NONE,
            name = name ?: MpUnitTypeModel.NONE.name,
            description = description ?: MpUnitTypeModel.NONE.description,
            synonyms = synonyms?.toMutableSet() ?: MpUnitTypeModel.NONE.synonyms,
            symbol = symbol ?: MpUnitTypeModel.NONE.symbol,
            symbols = symbols?.toMutableSet() ?: MpUnitTypeModel.NONE.symbols,
            isBase = isBase ?: MpUnitTypeModel.NONE.isBase
        )

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val SYNONYMS = "synonyms"
        const val SYMBOL = "symbol"
        const val SYMBOLS = "symbols"
        const val IS_BASE = "is_base"
    }
}