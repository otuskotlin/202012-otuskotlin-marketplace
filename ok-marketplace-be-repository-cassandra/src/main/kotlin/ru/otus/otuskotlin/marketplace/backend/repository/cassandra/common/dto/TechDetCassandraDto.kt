package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.dto

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechDetIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechDetModel

@Entity
data class TechDetCassandraDto(
    @CqlName(ID)
    val id: String? = null,
    @CqlName(PARAM)
    val param: TechParamCassandraDto? = null,
    @CqlName(VALUE)
    val value: String? = null,
    @CqlName(UNIT_TYPE)
    val unit: UnitTypeCassandraDto? = null,
    @CqlName(COMPARABLE_VALUE)
    val comparableValue: Double? = null,
) {
    fun toModel() = MpTechDetModel(
        id = id?.let { MpTechDetIdModel(it) }?: MpTechDetModel.NONE.id,
        param = param?.toModel()?: MpTechDetModel.NONE.param,
        value = value?: MpTechDetModel.NONE.value,
        unit = unit?.toModel()?: MpTechDetModel.NONE.unit,
        comparableValue = comparableValue?: MpTechDetModel.NONE.comparableValue,
    )

    companion object {
        const val TYPE_NAME = "tech_det"
        const val ID = "id"
        const val PARAM = "param"
        const val VALUE = "value"
        const val UNIT_TYPE = "unit_type"
        const val COMPARABLE_VALUE = "comparable_value"

        fun of(model: MpTechDetModel) = TechDetCassandraDto(
            id = model.id.takeIf { it != MpTechDetModel.NONE.id }?.id,
            param = model.param.takeIf { it != MpTechDetModel.NONE.param }?.let { TechParamCassandraDto.of(it) },
            value = model.value.takeIf { it != MpTechDetModel.NONE.value },
            unit = model.unit.takeIf { it != MpTechDetModel.NONE.unit }?.let { UnitTypeCassandraDto.of(it) },
            comparableValue = model.comparableValue.takeIf { it != MpTechDetModel.NONE.comparableValue }
        )
    }
}
