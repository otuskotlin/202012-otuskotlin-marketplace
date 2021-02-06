package ru.otus.otuskotlin.marketrplace.common.kmp.validation

import runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ValidationTest {
    @Test
    fun createValidationTest() = runBlockingTest {
        val validator = ValidatorStringNonEmpty()
        val res = validator.validate("")
        assertEquals(false, res.isSuccess)
        assertTrue {
            res.errors.first().message.contains("empty")
        }
    }

    @Test
    fun infixValidationTest() = runBlockingTest {
        val validator = ValidatorIntInRange("age", 2, 5)
        val res = validator validate 8
        assertEquals(false, res.isSuccess)
        assertTrue {
            res.errors.first().message.contains("range")
        }
    }
}

class ValidatorIntInRange<T : Comparable<T>>(
    private val field: String,
    private val min: T,
    private val max: T
) {
    infix fun validate(sample: T): ValidationResult = if (sample in min..max) {
        ValidationResult.SUCCESS
    } else {
        ValidationResult(
            errors = listOf(
                ValidationFieldError(
                    message = "Value $sample for field $field exceeds range [$min, $max]",
                    field = field
                )
            )
        )
    }
}

class ValidatorStringNonEmpty {

    fun validate(sample: String?): ValidationResult {
        return if (sample.isNullOrBlank()) {
            ValidationResult(
                errors = listOf(
                    ValidationDefaultError(
                        message = "String must not be empty",
                    )
                )
            )
        } else {
            ValidationResult.SUCCESS
        }
    }

}

class ValidationResult(val errors: List<IValidationError>) {
    val isSuccess: Boolean
        get() = errors.isEmpty()

    companion object {
        val SUCCESS = ValidationResult(emptyList())
    }
}

data class ValidationDefaultError(
    override val message: String,
) : IValidationError

data class ValidationFieldError(
    override val message: String,
    override val field: String,
) : IValidationError, IValidationFieldError

interface IValidationError {
    val message: String
}

interface IValidationFieldError : IValidationError {
    val field: String
}
