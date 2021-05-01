package ru.otus.otuskotlin.marketplace.common.backend.repositories

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel

interface IDemandRepository: IAdRepository<MpDemandModel> {

    companion object {
        val NONE = object : IDemandRepository {
            override suspend fun read(context: MpBeContext): MpDemandModel {
                TODO("Not yet implemented")
            }

            override suspend fun create(context: MpBeContext): MpDemandModel {
                TODO("Not yet implemented")
            }

            override suspend fun update(context: MpBeContext): MpDemandModel {
                TODO("Not yet implemented")
            }

            override suspend fun delete(context: MpBeContext): MpDemandModel {
                TODO("Not yet implemented")
            }

            override suspend fun list(context: MpBeContext): Collection<MpDemandModel> {
                TODO("Not yet implemented")
            }

            override suspend fun offers(context: MpBeContext): Collection<MpDemandModel> {
                TODO("Not yet implemented")
            }

        }
    }
}
