package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechParamIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechParamModel

@Entity
data class TechParamCassandraDto(
    @CqlName(ID)
    val id: String? = null,
    @CqlName(NAME)
    val name: String? = null,
    @CqlName(DESCRIPTION)
    val description: String? = null,
    @CqlName(PRIORITY)
    val priority: Double? = null,
    @CqlName(UNITS)
    val units: Set<UnitTypeCassandraDto>? = null,
) {
    fun toModel() = MpTechParamModel(
        id = id?.let { MpTechParamIdModel(it) }?: MpTechParamIdModel.NONE,
        name = name?: MpTechParamModel.NONE.name,
        description = description?: MpTechParamModel.NONE.description,
        priority = priority?: MpTechParamModel.NONE.priority,
        units = units?.map { it.toModel() }?.toMutableSet()?: MpTechParamModel.NONE.units,
    )

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val PRIORITY = "priority"
        const val UNITS = "units"

        fun of(model: MpTechParamModel) = TechParamCassandraDto(
            id = model.id.takeIf { it != MpTechParamModel.NONE.id }?.id,
            name = model.name.takeIf { it != MpTechParamModel.NONE.name },
            description = model.description.takeIf { it != MpTechParamModel.NONE.description },
            priority = model.priority.takeIf { it != MpTechParamModel.NONE.priority },
            units = model.units.takeIf { it != MpTechParamModel.NONE.units }?.map { UnitTypeCassandraDto.of(it) }?.toSet(),
        )
    }
}
