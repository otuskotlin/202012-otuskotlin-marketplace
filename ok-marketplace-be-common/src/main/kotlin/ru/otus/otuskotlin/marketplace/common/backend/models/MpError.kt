package ru.otus.otuskotlin.marketplace.common.backend.models

data class MpError(
    override val code: String = "",
    override val group: IMpError.Group = IMpError.Group.NONE,
    override val field: String = "",
    override val level: IMpError.Level = IMpError.Level.ERROR,
    override val message: String = ""
) : IMpError
