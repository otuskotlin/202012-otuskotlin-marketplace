package ru.otus.otuskotlin.marketplace.common.be

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test

class JUnit5Test {
    @Test
    fun junit5Test() {

    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "one",
            "two",
            "three",
        ]
    )
    fun paramTest(param: String) {
        println(param)
    }
}
