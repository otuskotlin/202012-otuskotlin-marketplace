package ru.otus.otuskotlin.marketplace.common.backend.exceptions

class MpRepoIndexException(index: String = "") : RuntimeException("Objects not found by index: $index")
