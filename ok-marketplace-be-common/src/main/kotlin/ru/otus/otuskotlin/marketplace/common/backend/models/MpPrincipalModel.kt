package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpPrincipalModel(
    val id: MpUserIdModel = MpUserIdModel.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: List<MpUserGroups> = emptyList()
) {
    companion object {
        val NONE = MpPrincipalModel()
    }
}
