package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

interface IMpItemCreateDto {
    val avatar: String?
    val title: String?
    val description: String?
    val tagIds: Set<String>?
    val techDets: Set<TechDetsDto>?
}
