package ru.otus.otuskotlin.marketplace.backend.repository.inmemory.demands

import org.cache2k.Cache
import org.cache2k.Cache2kBuilder
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoIndexException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoNotFoundException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoWrongIdException
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpSortModel
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
        val model = cache.get(id.id)?.toModel()?: throw MpRepoNotFoundException(id.id)
        context.responseDemand = model
        return model
    }

    override suspend fun create(context: MpBeContext): MpDemandModel {
        val dto = DemandInMemoryDto.of(context.requestDemand, UUID.randomUUID().toString())
        val model = save(dto).toModel()
        context.responseDemand = model
        return model
    }

    override suspend fun update(context: MpBeContext): MpDemandModel {
        if (context.requestDemand.id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(context.requestDemand.id.id)
        val model = save(DemandInMemoryDto.of(context.requestDemand)).toModel()
        context.responseDemand = model
        return model
    }

    override suspend fun delete(context: MpBeContext): MpDemandModel {
        val id = context.requestDemandId
        if (id == MpDemandIdModel.NONE) throw MpRepoWrongIdException(id.id)
        val model = cache.peekAndRemove(id.id)?.toModel()?: throw MpRepoNotFoundException(id.id)
        context.responseDemand = model
        return model
    }

    override suspend fun list(context: MpBeContext): Collection<MpDemandModel> {
        val textFilter = context.demandFilter.text
        if (textFilter.length < 3) throw MpRepoIndexException(textFilter)
        val records = cache.asMap().filterValues {
            it.title?.contains(textFilter, true)?:false || if (context.demandFilter.includeDescription) {
                it.description?.contains(textFilter, true) ?: false
            } else false
        }.values
        if (records.count() <= context.demandFilter.offset)
            throw MpRepoIndexException(textFilter)
        val list = records.toList()
            .subList(
                context.demandFilter.offset,
                if (records.count() >= context.demandFilter.offset + context.demandFilter.count)
                    context.demandFilter.offset + context.demandFilter.count
                else records.count()
            ).map { it.toModel() }
        context.responseDemands = list.toMutableList()
        context.pageCount = list.count().takeIf { it != 0 }
            ?.let { (records.count().toDouble() / it + 0.5).toInt() }
            ?: Int.MIN_VALUE
        return list
    }

    override suspend fun offers(context: MpBeContext): Collection<MpDemandModel> {
        var title = context.requestProposal.title
        if (title.length < 3) throw MpRepoIndexException(title)
        var offers: List<DemandInMemoryDto> = emptyList()
        while (title.length >= 3 && offers.count() < 10) {
            offers = cache.asMap().filterValues {
                it.title?.contains(title, true)?: false
            }.values.toList()
            title = title.dropLast(1)
        }
        val list = offers.takeIf { it.isNotEmpty() }?.map { it.toModel() }?: throw  MpRepoIndexException(title)
        context.responseDemands = list.toMutableList()
        return list
    }

    private suspend fun save(dto: DemandInMemoryDto): DemandInMemoryDto {
        cache.put(dto.id, dto)
        return cache.get(dto.id)
    }
}
