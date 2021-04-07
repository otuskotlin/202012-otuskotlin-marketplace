package ru.otus.otuskotlin.marketplace.backend.repository.inmemory.demands

import org.cache2k.Cache
import org.cache2k.Cache2kBuilder
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoNotFoundException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoWrongIdException
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IDemandRepository
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class DemandRepoInMemory @OptIn(ExperimentalTime::class) constructor(
    ttl: Duration,
    initObjects: Collection<MpDemandModel> = emptyList()
): IDemandRepository {
    @OptIn(ExperimentalTime::class)
    private var cache: Cache<String, DemandInMemoryDto> = object : Cache2kBuilder<String, DemandInMemoryDto>() {}
        .expireAfterWrite(ttl.toLongMilliseconds(), TimeUnit.MILLISECONDS) // expire/refresh after 5 minutes
        .suppressExceptions(false)
        .build()
        .also { cache ->
            initObjects.forEach {
                cache.put(it.id.id, DemandInMemoryDto.of(it))
            }
        }

    override suspend fun read(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return cache.get(id.id)?.toModel()?: throw MpRepoNotFoundException(id.id)
    }

    override suspend fun create(context: MpBeContext): MpDemandModel {
        val dto = DemandInMemoryDto.of(context.requestDemand, UUID.randomUUID().toString())
        return save(dto).toModel()
    }

    override suspend fun update(context: MpBeContext): MpDemandModel {
        if (context.requestDemand.id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(context.requestDemand.id.id)
        return save(DemandInMemoryDto.of(context.requestDemand)).toModel()
    }

    override suspend fun delete(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return cache.peekAndRemove(id.id)?.toModel()?: throw MpRepoNotFoundException(id.id)
    }

    override suspend fun list(context: MpBeContext): Collection<MpDemandModel> {
        TODO("Not yet implemented")
    }

    override suspend fun offers(context: MpBeContext): Collection<MpDemandModel> {
        TODO("Not yet implemented")
    }

    private suspend fun save(dto: DemandInMemoryDto): DemandInMemoryDto {
        cache.put(dto.id, dto)
        return cache.get(dto.id)
    }
}
