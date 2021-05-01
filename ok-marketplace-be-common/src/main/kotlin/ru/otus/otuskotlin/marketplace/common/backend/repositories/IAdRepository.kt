package ru.otus.otuskotlin.marketplace.common.backend.repositories

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.IMpItemModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel

interface IAdRepository<T: IMpItemModel> {
    suspend fun read(context: MpBeContext): T
    suspend fun create(context: MpBeContext): T
    suspend fun update(context: MpBeContext): T
    suspend fun delete(context: MpBeContext): T

    suspend fun list(context: MpBeContext): Collection<T>
    suspend fun offers(context: MpBeContext): Collection<T>
}
