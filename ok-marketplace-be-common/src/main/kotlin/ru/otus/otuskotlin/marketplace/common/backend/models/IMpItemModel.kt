package ru.otus.otuskotlin.marketplace.common.backend.models

interface IMpItemModel{
    val id: IMpItemIdModel
    val avatar: String
    val title: String
    val description: String
    val owner: MpUserModel
    val tagIds: MutableSet<String>
    val techDets: MutableSet<MpTechDetModel>
}
