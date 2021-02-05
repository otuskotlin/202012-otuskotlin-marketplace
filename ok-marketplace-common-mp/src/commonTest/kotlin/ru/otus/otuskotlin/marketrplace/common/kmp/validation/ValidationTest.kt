package ru.otus.otuskotlin.marketrplace.common.kmp.validation

import runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ValidationTest {
    @Test
    fun createValidationTest() = runBlockingTest {
        val validator = ValidatorStringNonEmpty()
        val res = validadtor.validate("")
        assertEquals(false, res.isSuccess)
        assertTrue {
            res.errors.first().message.contains("empty")
        }
    }

    @Test
    fun infixValidationTest() = runBlockingTest {
        val validator = ValidatorIntInRange(2, 5)
        val res = validator validate 8
        assertEquals(false, res.isSuccess)
        assertTrue {
            res.errors.first().message.contains("range")
        }
    }
}
