package ru.otus.otuskotlin.marketplace.backend.mappers

import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechParamDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.UnitTypeDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpDemandDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpProposalDto

internal fun MpProposalModel.toTransport() = MpProposalDto(
    id = id.id.takeIf { it.isNotBlank() },
    avatar = avatar.takeIf { it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    tagIds = tagIds.takeIf { it.isNotEmpty() },
    techDets = techDets.takeIf { it.isNotEmpty() }
        ?.filter { it != MpTechDetModel.NONE }?.map { it.toTransport() }?.toSet()
)


internal fun MpDemandModel.toTransport() = MpDemandDto(
    id = id.id.takeIf { it.isNotBlank() },
    avatar = avatar.takeIf { it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    tagIds = tagIds.takeIf { it.isNotEmpty() },
    techDets = techDets.takeIf { it.isNotEmpty() }
        ?.filter { it != MpTechDetModel.NONE }?.map { it.toTransport() }?.toSet()
)

internal fun MpTechDetModel.toTransport() = TechDetsDto(
    id = id.id.takeIf { it.isNotBlank() },
    param = param.takeIf { it != MpTechParamModel.NONE }?.toTransport(),
    value = value.takeIf { it.isNotBlank() },
    unit = unit.takeIf { it != MpUnitTypeModel.NONE }?.toTransport(),
    comparableValue = comparableValue.takeIf { it != Double.MIN_VALUE },
)

internal fun MpTechParamModel.toTransport() = TechParamDto(
    id = id.id.takeIf { it.isNotBlank() },
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    priority = priority.takeIf { it != Double.MIN_VALUE },
    units = units.takeIf { it.isNotEmpty() }?.filter { it != MpUnitTypeModel.NONE }
        ?.map { it.toTransport() }?.toSet()
)

internal fun MpUnitTypeModel.toTransport() = UnitTypeDto(
    id = id.id.takeIf { it.isNotBlank() },
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    synonyms = synonyms.takeIf { it.isNotEmpty() },
    symbol = symbol.takeIf { it.isNotBlank() },
    symbols = symbols.takeIf { it.isNotEmpty() },
    isBase = isBase,
)

internal fun TechDetsDto.toModel() = MpTechDetModel(
    id = id?.let { MpTechDetIdModel(it) }?: MpTechDetIdModel.NONE,
    param = param?.toModel()?: MpTechParamModel.NONE,
    value = value?: "",
    unit = unit?.toModel()?: MpUnitTypeModel.NONE,
    comparableValue = comparableValue?: Double.MIN_VALUE,
)

internal fun UnitTypeDto.toModel() = MpUnitTypeModel(
    id = id?.let { MpUnitTypeIdModel(it) }?: MpUnitTypeIdModel.NONE,
    name = name?: "",
    description = description?: "",
    synonyms = synonyms?.toMutableSet()?: mutableSetOf(),
    symbol = symbol?: "",
    symbols = symbols?.toMutableSet()?: mutableSetOf(),
    isBase = isBase?: false,
)

internal fun TechParamDto.toModel() = MpTechParamModel(
    id = id?.let { MpTechParamIdModel(it) }?: MpTechParamIdModel.NONE,
    name = name?: "",
    description = description?: "",
    priority = priority?: Double.MIN_VALUE,
    units = units?.map { it.toModel() }?.toMutableSet()?: mutableSetOf(),
)
