package ru.otus.otuskotlin.marketplace.common.backend.repositories

object EmptyUserSession : IUserSession<Any> {
    override val fwSession = object {}
}
