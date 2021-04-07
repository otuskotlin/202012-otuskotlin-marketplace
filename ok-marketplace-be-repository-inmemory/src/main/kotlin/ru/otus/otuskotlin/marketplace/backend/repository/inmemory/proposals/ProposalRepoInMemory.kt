package ru.otus.otuskotlin.marketplace.backend.repository.inmemory.proposals

import org.cache2k.Cache
import org.cache2k.Cache2kBuilder
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
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
        return cache.get(id.id)?.toModel()?: throw MpRepoNotFoundException(id.id)
    }

    override suspend fun create(context: MpBeContext): MpProposalModel {
        val dto = ProposalInMemoryDto.of(context.requestProposal, UUID.randomUUID().toString())
        return save(dto).toModel()
    }

    override suspend fun update(context: MpBeContext): MpProposalModel {
        if (context.requestProposal.id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(context.requestProposal.id.id)
        return save(ProposalInMemoryDto.of(context.requestProposal)).toModel()
    }

    override suspend fun delete(context: MpBeContext): MpProposalModel {
        val id = context.requestProposalId
        if (id == MpProposalIdModel.NONE) throw MpRepoWrongIdException(id.id)
        return cache.peekAndRemove(id.id)?.toModel()?: throw MpRepoNotFoundException(id.id)
    }

    override suspend fun list(context: MpBeContext): Collection<MpProposalModel> {
        TODO("Not yet implemented")
    }

    override suspend fun offers(context: MpBeContext): Collection<MpProposalModel> {
        TODO("Not yet implemented")
    }

    private suspend fun save(dto: ProposalInMemoryDto): ProposalInMemoryDto {
        cache.put(dto.id, dto)
        return cache.get(dto.id)
    }

}
