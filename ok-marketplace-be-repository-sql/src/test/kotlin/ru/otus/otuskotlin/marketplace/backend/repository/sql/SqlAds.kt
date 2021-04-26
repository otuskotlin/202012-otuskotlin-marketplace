package ru.otus.otuskotlin.marketplace.backend.repository.sql

import org.testcontainers.containers.PostgreSQLContainer
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import java.time.Duration

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres")

internal open class SqlAds {

    companion object {
        private const val USER = "postgres"
        private const val PASS = "postgres"
        private const val DB = "marketplace"

        val testDemandId1 = MpDemandIdModel("11111111-1111-1111-1111-111111112111")
        val testDemandId2 = MpDemandIdModel("11111111-1111-1111-1111-111111112112")
        val testDemandId3 = MpDemandIdModel("11111111-1111-1111-1111-111111112113")
        val testDemandId4 = MpDemandIdModel("11111111-1111-1111-1111-111111112114")
        val testDemandId5 = MpDemandIdModel("11111111-1111-1111-1111-111111112115")
        val expectedDemandId = MpDemandIdModel("11111111-1111-1111-1111-111111112120")

        val testProposalId1 = MpProposalIdModel("11111111-1111-1111-1111-111111113111")
        val testProposalId2 = MpProposalIdModel("11111111-1111-1111-1111-111111113112")
        val testProposalId3 = MpProposalIdModel("11111111-1111-1111-1111-111111113113")
        val testProposalId4 = MpProposalIdModel("11111111-1111-1111-1111-111111113114")
        val testProposalId5 = MpProposalIdModel("11111111-1111-1111-1111-111111113115")
        val expectedProposalId = MpProposalIdModel("11111111-1111-1111-1111-111111113120")

        const val tag1Id = "21111111-1111-1111-1111-111111111111"
        const val tag2Id = "21111111-1111-1111-1111-111111111112"
        const val tag3Id = "21111111-1111-1111-1111-111111111113"

        private val container by lazy {
            PostgresContainer().apply {
                withUsername(USER)
                withPassword(PASS)
                withDatabaseName(DB)
                withStartupTimeout(Duration.ofSeconds(300L))
                start()
            }
        }

        val url by lazy { container.jdbcUrl }

        val unit = MpUnitTypeModel(
            name = "weight",
            synonyms = mutableSetOf("вес"),
            symbol = "kg",
            symbols = mutableSetOf("Kg")
        )

        val param = MpTechParamModel(
            name = "nett",
//                units = mutableSetOf(unit)
        )

        val techDet = MpTechDetModel(
//                param = param,
//                unit = unit,
            value = "100"
        )

        val repoDemand by lazy {
            DemandRepoSql(
                url = url,
                user = USER,
                password = PASS,
                printLogs = true,
//                keyspaceName = keyspace,
//                hosts = container.host,
//                port = container.getMappedPort(PORT),
//                testing = true,
                initObjects = listOf(
                    MpDemandModel(
                        id = testDemandId1,
                        title = "to-update-demand",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpDemandModel(
                        id = testDemandId2,
                        title = "test-demand1",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpDemandModel(
                        id = testDemandId3,
                        title = "demand-0",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpDemandModel(
                        id = testDemandId4,
                        title = "test-demand2",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpDemandModel(
                        id = testDemandId5,
                        title = "demand-to-delete",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                )
            )
        }

        val repoProposal by lazy {
            ProposalRepoSql(
                url = url,
                user = USER,
                password = PASS,
                printLogs = true,
//                keyspaceName = keyspace,
//                hosts = container.host,
//                port = container.getMappedPort(PORT),
//                testing = true,
                initObjects = listOf(
                    MpProposalModel(
                        id = testProposalId1,
                        title = "to-update-proposal",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpProposalModel(
                        id = testProposalId2,
                        title = "test-proposal1",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpProposalModel(
                        id = testProposalId3,
                        title = "proposal-0",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpProposalModel(
                        id = testProposalId4,
                        title = "test-proposal2",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpProposalModel(
                        id = testProposalId5,
                        title = "proposal-to-delete",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                )
            )
        }
    }
}
