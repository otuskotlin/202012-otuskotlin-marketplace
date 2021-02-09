package ru.otus.otuskotlin.marketplace.backend.common.models

interface IMpItemModel{
    val id: IMpItemIdModel
    val avatar: String
    val title: String
    val description: String
    val tagIds: MutableSet<String>
    val techDets: MutableSet<MpTechDetModel>
}
