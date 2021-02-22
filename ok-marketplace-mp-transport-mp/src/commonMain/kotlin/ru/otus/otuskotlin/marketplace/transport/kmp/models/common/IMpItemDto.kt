package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

interface IMpItemDto: IMpItemUpdateDto {
    val permissions: Set<MpItemPermission>?
}
