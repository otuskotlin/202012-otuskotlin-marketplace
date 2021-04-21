package ru.otus.otuskotlin.marketplace.common.backend.exceptions

class MpRepoNotFoundException(id: String): RuntimeException("Object with ID=$id is not found")
