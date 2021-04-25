package ru.otus.otuskotlin.marketplace.common.backend.exceptions

class MpRepoModifyException(id: String) : Throwable("Cannot modify record with id = $id")
