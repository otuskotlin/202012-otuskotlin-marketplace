package ru.otus.otuskotlin.marketplace.kmp.pipelines.validation

import ru.otus.otuskotlin.marketplace.common.kmp.validation.IValidationError
import ru.otus.otuskotlin.marketplace.common.kmp.validation.IValidator
import ru.otus.otuskotlin.marketplace.common.kmp.validation.ValidationDefaultError
import ru.otus.otuskotlin.marketplace.common.kmp.validation.ValidationResult
import ru.otus.otuskotlin.marketplace.common.kmp.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.marketplace.pipelines.kmp.Pipeline
import ru.otus.otuskotlin.marketplace.pipelines.kmp.pipeline
import runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ValidationTest {

    @Test
    fun pipelineValidation() {
        val pl = pipeline<TestContext> {

            validation {
                errorHandler { v: ValidationResult ->
                    if (v.isSuccess) return@errorHandler
                    errors.add(v.errors)
                }

                validate(ValidatorStringNonEmpty()) on { x }
                validate(ValidatorStringNonEmpty()) on { y }
            }
        }
        runBlockingTest {
            val ctx = TestContext()
            pl.execute(ctx)
            assertEquals(2, ctx.errors.size)
        }
    }

    data class TestContext(
        val x: String = "",
        val y: String = "",
        val errors: List<IValidationError> = mutableListOf()
    )
}
