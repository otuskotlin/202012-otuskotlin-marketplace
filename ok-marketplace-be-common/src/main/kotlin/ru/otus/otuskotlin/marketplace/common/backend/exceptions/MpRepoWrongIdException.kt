package ru.otus.otuskotlin.marketplace.common.backend.exceptions

class MpRepoWrongIdException(id: String): Throwable("Wrong ID in operation: $id")
