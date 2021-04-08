package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

interface IMpFilterItem {
    val text: String?
    val sortBy: MpSortDto?
    val offset: Int?
    val count: Int?
}
