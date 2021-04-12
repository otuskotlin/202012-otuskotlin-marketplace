package ru.otus.otuskotlin.marketplace.backend.repository.inmemory.proposals

import org.cache2k.Cache
import org.cache2k.Cache2kBuilder
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoIndexException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoNotFoundException
import ru.otus.otuskotlin.marketplace.common.backend.exceptions.MpRepoWrongIdException
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IProposalRepository
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class ProposalRepoInMemory @OptIn(ExperimentalTime::class) constructor(
    ttl: Duration,
    initObjects: Collection<MpProposalModel> = emptyList()
): IProposalRepository {
    @OptIn(ExperimentalTime::class)
    private var cache: Cache<String, ProposalInMemoryDto> = object : Cache2kBuilder<String, ProposalInMemoryDto>() {}
        .expireAfterWrite(ttl.toLongMilliseconds(), TimeUnit.MILLISECONDS) // expire/refresh after 5 minutes
        .suppressExceptions(false)
        .build()
        .also { cache ->
            initObjects.forEach {
                cache.put(it.id.id, ProposalInMemoryDto.of(it))
            }
        }

    override suspend fun read(context: MpBeContext): MpProposalModel {
        val id = context.requestProposalId
        if (id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(id.id)
        val  model = cache.get(id.id)?.toModel()?: throw MpRepoNotFoundException(id.id)
        context.responseProposal = model
        return model
    }

    override suspend fun create(context: MpBeContext): MpProposalModel {
        val dto = ProposalInMemoryDto.of(context.requestProposal, UUID.randomUUID().toString())
        val model = save(dto).toModel()
        context.responseProposal = model
        return model
    }

    override suspend fun update(context: MpBeContext): MpProposalModel {
        if (context.requestProposal.id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(context.requestProposal.id.id)
        val model = save(ProposalInMemoryDto.of(context.requestProposal)).toModel()
        context.responseProposal = model
        return model
    }

    override suspend fun delete(context: MpBeContext): MpProposalModel {
        val id = context.requestProposalId
        if (id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(id.id)
        val model = cache.peekAndRemove(id.id)?.toModel()?: throw MpRepoNotFoundException(id.id)
        context.responseProposal = model
        return model
    }

    override suspend fun list(context: MpBeContext): Collection<MpProposalModel> {
        val textFilter = context.proposalFilter.text
        if (textFilter.length < 3) throw MpRepoIndexException(textFilter)
        val records = cache.asMap().filterValues {
            it.title?.contains(textFilter, true)?:false || if (context.proposalFilter.includeDescription) {
                it.description?.contains(textFilter, true) ?: false
            } else false
        }.values
        if (records.count() <= context.proposalFilter.offset)
            throw MpRepoIndexException(textFilter)
        val list = records.toList()
            .subList(
                context.proposalFilter.offset,
                if (records.count() >= context.proposalFilter.offset + context.proposalFilter.count)
                    context.proposalFilter.offset + context.proposalFilter.count
                else records.count()
            ).map { it.toModel() }
        context.responseProposals = list.toMutableList()
        context.pageCount = list.count().takeIf { it != 0 }
            ?.let { (records.count().toDouble() / it + 0.5).toInt() }
            ?: Int.MIN_VALUE
        return list
    }

    override suspend fun offers(context: MpBeContext): Collection<MpProposalModel> {
        var title = context.requestDemand.title
        if (title.length < 3) throw MpRepoIndexException(title)
        var offers: List<ProposalInMemoryDto> = emptyList()
        while (title.length >= 3 && offers.count() < 10) {
            offers = cache.asMap().filterValues {
                it.title?.contains(title, true)?: false
            }.values.toList()
            title = title.dropLast(1)
        }
        val list = offers.takeIf { it.isNotEmpty() }?.map { it.toModel() }?: throw  MpRepoIndexException(title)
        context.responseProposals = list.toMutableList()
        return list
    }

    private suspend fun save(dto: ProposalInMemoryDto): ProposalInMemoryDto {
        cache.put(dto.id, dto)
        return cache.get(dto.id)
    }

}
