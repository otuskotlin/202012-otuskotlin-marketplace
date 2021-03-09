package ru.otus.otuskotlin.marketrplace.common.kmp.pipelines

import ru.otus.otuskotlin.marketplace.common.kmp.pipelines.operation
import ru.otus.otuskotlin.marketplace.common.kmp.pipelines.pipeline
import runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PipelineTest {
    @Test
    fun simplePipeline() {
        val givenOperation = operation<TestContext> {
            run { a += "c" }
        }
        val givenPipeline = pipeline<TestContext> {
            run { a = "a" }
            run(givenOperation)
            operation {
                startIf {
                    println("Check a: $a")
                    a.isNotEmpty()
                }
                run { a += "b" }
            }
        }
        val givenContext = TestContext()
        println("Starting test")
        runBlockingTest {
            givenPipeline.run(givenContext)
            assertEquals("acb", givenContext.a)
            println("Test Done")
        }
        println("Finish test")
    }

    @Test
    fun nestedPipeline() {
        val givenOperation = operation<TestContext> {
            run { a += "c" }
        }
        val givenPipeline = pipeline<TestContext> {
            run { a = "a" }
            pipeline {
                startIf { true }
                run(givenOperation)
                operation {
                    startIf {
                        println("Check a: $a")
                        a.isNotEmpty()
                    }
                    run { a += "b" }
                }
            }
        }
        val givenContext = TestContext()
        println("Starting test")
        runBlockingTest {
            givenPipeline.run(givenContext)
            assertEquals("acb", givenContext.a)
            println("Test Done")
        }
        println("Finish test")
    }

    data class TestContext(
        var a: String = ""
    )
}