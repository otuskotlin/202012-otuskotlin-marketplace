package ru.otus.otuskotlin.marketplace.kmp.pipelines.validation

import ru.otus.otuskotlin.marketplace.common.kmp.validation.IValidationError
import ru.otus.otuskotlin.marketplace.common.kmp.validation.IValidator
import ru.otus.otuskotlin.marketplace.common.kmp.validation.ValidationResult
import ru.otus.otuskotlin.marketplace.common.kmp.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.marketplace.pipelines.kmp.*
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
                    errors.addAll(v.errors)
                }

                validate<String?> { validator(ValidatorStringNonEmpty()); on { x } }
                validate<String?> { validator(ValidatorStringNonEmpty()); on { y } }
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
        val errors: MutableList<IValidationError> = mutableListOf()
    )
}

private fun <C> Pipeline.Builder<C>.validation(block: ValidationBuilder<C>.() -> Unit) {
    execute(ValidationBuilder<C>().apply(block).build())
}

class ValidationBuilder<C>: IOperationBuilder<C> {
    private var errorHandler: C.(ValidationResult) -> Unit = {}
    private val validators: MutableList<IValidationOperation<C,*>> = mutableListOf()

    fun errorHandler(block: C.(ValidationResult) -> Unit) {
        errorHandler = block
    }

    fun <T> validate(block: ValidationOperationBuilder<C,T>.() -> Unit) {
        val builder = ValidationOperationBuilder<C,T>(errorHandler).apply(block)
        validators.add(builder.build())
    }

    override fun build(): IOperation<C> = PipelineValidation(validators)

}

class PipelineValidation<C>(
    private val validations: List<IValidationOperation<C,*>>
) : IOperation<C> {
    override suspend fun execute(context: C) {
        validations.forEach {
            it.execute(context)
        }
    }

}

@PipelineDsl
class ValidationOperationBuilder<C, T>(
    private var errorHandler: C.(ValidationResult) -> Unit = {}
) {
    private lateinit var onBlock: C.() -> T
    private lateinit var validator: IValidator<T>
    fun validator(validator: IValidator<T>) {
        this.validator = validator
    }
    fun on(block: C.() -> T) {
        onBlock = block
    }
    fun build(): IValidationOperation<C,T> {
        return DefaultValidationOperation(
            validator = validator,
            onBlock = onBlock,
            errorHandler = errorHandler
        )
    }
}

class DefaultValidationOperation<C, T>(
    private val onBlock: C.() -> T,
    private val validator: IValidator<T>,
    private var errorHandler: C.(ValidationResult) -> Unit = {}
): IValidationOperation<C, T> {
    override suspend fun execute(context: C) {
        val value = context.onBlock()
        val res = validator.validate(value)
        context.errorHandler(res)
    }

}

interface IValidationOperation<C,T>: IOperation<C>
