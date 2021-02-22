import models.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechParamDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.UnitTypeDto

fun UnitTypeDto.toInternal() = UnitTypeModel(
    id = id?.let { UnitTypeIdModel(it) } ?: UnitTypeIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    synonyms = synonyms?.toMutableSet() ?: mutableSetOf(),
    symbol = symbol ?: "",
    isBase = isBase ?: false,
)

fun TechDetsDto.toInternal() = TechDetModel(
    id = id?.let { TechDetIdModel(it) } ?: TechDetIdModel.NONE,
    param = param?.toInternal() ?: TechParamModel.NONE,
)

fun TechParamDto.toInternal() = TechParamModel(
    id = id?.let { TechParamIdModel(it) } ?: TechParamIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    priority = priority ?: Double.MIN_VALUE,
    units = units?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
)

