package ru.otus.otuskotlin.marketplace.common.backend.models

interface IMpItemFilterModel {
    val text: String
    val includeDescription: Boolean
    val sortBy: MpSortModel
    val offset: Int
    val count: Int
}
