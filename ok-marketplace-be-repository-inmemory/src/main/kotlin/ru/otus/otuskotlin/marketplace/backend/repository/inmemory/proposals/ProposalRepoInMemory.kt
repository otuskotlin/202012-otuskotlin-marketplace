package ru.otus.otuskotlin.marketplace.backend.repository.inmemory.proposals

import org.cache2k.Cache
import org.cache2k.Cache2kBuilder
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IProposalRepository
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
        TODO("Not yet implemented")
    }

    override suspend fun create(context: MpBeContext): MpProposalModel {
        TODO("Not yet implemented")
    }

    override suspend fun update(context: MpBeContext): MpProposalModel {
        TODO("Not yet implemented")
    }

    override suspend fun delete(context: MpBeContext): MpProposalModel {
        TODO("Not yet implemented")
    }

    override suspend fun list(context: MpBeContext): Collection<MpProposalModel> {
        TODO("Not yet implemented")
    }

    override suspend fun offers(context: MpBeContext): Collection<MpProposalModel> {
        TODO("Not yet implemented")
    }

}
