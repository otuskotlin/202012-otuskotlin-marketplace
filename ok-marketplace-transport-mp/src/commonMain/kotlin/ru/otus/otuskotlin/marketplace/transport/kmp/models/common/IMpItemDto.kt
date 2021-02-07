package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

interface IMpItemDto {
    val id: String?
    val avatar: String?
    val title: String?
    val descriptor: String?
    val tags: Set<TagDto>?
    val techDets: Set<TechDetsDto>?
}
