package ru.otus.otuskotlin.marketplace.backend.repoDemandsitory.sql

import ru.otus.otuskotlin.marketplace.backend.repository.sql.SqlTestCompanion
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IDemandRepository

internal class SqlDemandsRepoTest: DemandsRepoBaseTest() {
    override val testDemandId1: MpDemandIdModel
        get() = SqlTestCompanion.testDemandId1
    override val testDemandId2: MpDemandIdModel
        get() = SqlTestCompanion.testDemandId2
    override val testDemandId3: MpDemandIdModel
        get() = SqlTestCompanion.testDemandId3
    override val testDemandId4: MpDemandIdModel
        get() = SqlTestCompanion.testDemandId4
    override val testDemandId5: MpDemandIdModel
        get() = SqlTestCompanion.testDemandId5
    override val expectedDemandId: MpDemandIdModel
        get() = SqlTestCompanion.expectedDemandId
    override val repoDemand: IDemandRepository
        get() = SqlTestCompanion.repoDemand
}
