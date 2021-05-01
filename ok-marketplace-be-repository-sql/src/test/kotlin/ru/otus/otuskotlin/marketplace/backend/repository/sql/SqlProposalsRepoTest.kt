package ru.otus.otuskotlin.marketplace.backend.repository.sql

import ru.otus.otuskotlin.marketplace.backend.repoProposalsitory.sql.ProposalsRepoBaseTest
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IProposalRepository

internal class SqlProposalsRepoTest: ProposalsRepoBaseTest() {
    override val testProposalId1: MpProposalIdModel
        get() = SqlTestCompanion.testProposalId1
    override val testProposalId2: MpProposalIdModel
        get() = SqlTestCompanion.testProposalId2
    override val testProposalId3: MpProposalIdModel
        get() = SqlTestCompanion.testProposalId3
    override val testProposalId4: MpProposalIdModel
        get() = SqlTestCompanion.testProposalId4
    override val testProposalId5: MpProposalIdModel
        get() = SqlTestCompanion.testProposalId5
    override val expectedProposalId: MpProposalIdModel
        get() = SqlTestCompanion.expectedProposalId
    override val repoProposal: IProposalRepository
        get() = SqlTestCompanion.repoProposal
}
