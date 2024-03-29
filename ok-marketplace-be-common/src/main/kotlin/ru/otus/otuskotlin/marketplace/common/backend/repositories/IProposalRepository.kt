package ru.otus.otuskotlin.marketplace.common.backend.repositories

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel

interface IProposalRepository: IAdRepository<MpProposalModel> {

    companion object {
        val NONE = object : IProposalRepository {
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
    }
}
