package ru.otus.otuskotlin.marketplace.backend.app.spring.services

import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.UnitTypeDto

class UnitTypeService {

    companion object {
        fun mockRead(id: String, symbol: String) = UnitTypeDto(
            id = id,
            name = symbol,
            description = "Description of the $symbol",
            symbol = symbol,
            isBase = false
        )
    }
}
