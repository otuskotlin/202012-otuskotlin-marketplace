package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import ru.otus.otuskotlin.marketplace.common.backend.models.MpUnitTypeIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpUnitTypeModel

@Entity
data class UnitTypeCassandraDto(
    @CqlName(ID)
    val id: String? = null,
    @CqlName(NAME)
    val name: String? = null,
    @CqlName(DESCRIPTION)
    val description: String? = null,
    @CqlName(SYNONYMS)
    val synonyms: Set<String>? = null,
    @CqlName(SYMBOL)
    val symbol: String? = null,
    @CqlName(SYMBOLS)
    val symbols: Set<String>? = null,
    @CqlName(IS_BASE)
    val isBase: Boolean? = null,
) {
    fun toModel() = MpUnitTypeModel(
        id = id?.let { MpUnitTypeIdModel(it) }?: MpUnitTypeIdModel.NONE,
        name = name?: MpUnitTypeModel.NONE.name,
        description = description?: MpUnitTypeModel.NONE.description,
        synonyms = synonyms?.toMutableSet()?: MpUnitTypeModel.NONE.synonyms,
        symbol = symbol?: MpUnitTypeModel.NONE.symbol,
        symbols = symbols?.toMutableSet()?: MpUnitTypeModel.NONE.symbols,
        isBase = isBase?: MpUnitTypeModel.NONE.isBase,
    )

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val SYNONYMS = "synonyms"
        const val SYMBOL = "symbol"
        const val SYMBOLS = "symbols"
        const val IS_BASE = "is_base"

        fun of(model: MpUnitTypeModel) = UnitTypeCassandraDto(
            id = model.id.takeIf { it != MpUnitTypeModel.NONE.id }?.id,
            name = model.name.takeIf { it != MpUnitTypeModel.NONE.name },
            description = model.description.takeIf { it != MpUnitTypeModel.NONE.description },
            synonyms = model.synonyms.takeIf { it != MpUnitTypeModel.NONE.synonyms },
            symbol = model.symbol.takeIf { it != MpUnitTypeModel.NONE.symbol },
            symbols = model.symbols.takeIf { it != MpUnitTypeModel.NONE.symbols },
            isBase = model.isBase.takeIf { it != MpUnitTypeModel.NONE.isBase },
        )
    }
}
