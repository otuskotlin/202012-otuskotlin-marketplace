package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.producers

import com.datastax.oss.driver.api.mapper.result.MapperResultProducer
import com.datastax.oss.driver.api.mapper.result.MapperResultProducerService

/**
 * Важен порядок в списке продюсеров, поиск подходящего продюссера в маппере производится последовательно
 */
class GuavaFutureProducerService: MapperResultProducerService {
    override fun getProducers(): Iterable<MapperResultProducer> =
        listOf(
            FutureOfCollectionProducer(),
            FutureOfUnitProducer(),
            FutureOfEntityProducer(),
        )
}
