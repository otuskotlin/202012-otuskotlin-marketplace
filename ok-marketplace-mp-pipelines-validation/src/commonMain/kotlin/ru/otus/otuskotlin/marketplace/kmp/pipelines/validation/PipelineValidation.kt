package ru.otus.otuskotlin.marketplace.kmp.pipelines.validation

import ru.otus.otuskotlin.marketplace.pipelines.kmp.IOperation
import ru.otus.otuskotlin.marketplace.pipelines.kmp.Predicate

class PipelineValidation<C>(
    private val validations: List<IValidationOperation<C,*>>,
    private val checkPrecondition: Predicate<C> = { true },
    ) : IOperation<C> {
    override suspend fun execute(context: C) {
        if (context.checkPrecondition()) {
            validations.forEach {
                it.execute(context)
            }
        }
    }
}
