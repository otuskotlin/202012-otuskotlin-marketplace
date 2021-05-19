package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpUserModel(
    val id: MpUserIdModel = MpUserIdModel.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
) {
    companion object {
        val NONE = MpUserModel()
    }
}
