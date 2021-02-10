package ru.otus.otuskotlin.marketplace.common.be

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

data class StrlenTestRow(
    val str: String,
    val len: Int
)

class KotestTest : StringSpec({
    "Doing our first kotest" {
        "25".toInt() shouldBe 25
    }

    forAll(
        row("one", 3),
        row("two", 3),
        row("three", 5),
    ) { it ->
        it.a.length shouldBe it.b
    }

    forAll<StrlenTestRow>(
        "[one]" to StrlenTestRow("one", 3),
        "[two]" to StrlenTestRow("two", 3),
        "[three]" to StrlenTestRow("three", 5),
    ) { it ->
        it.str.length shouldBe it.len
    }
}) {
}
