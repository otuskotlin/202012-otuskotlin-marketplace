package ru.otus.otuskotlin.marketplace.backend.app.kotless

import io.kotless.dsl.lang.KotlessContext
import io.kotless.dsl.lang.http.Post
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechParamDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.UnitTypeDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*
import java.time.Instant

private val log = LoggerFactory.getLogger("DemandController")

@Post("/demand/list")
fun demandList(): String? = handle { query: MpRequestDemandList ->
    MpResponseDemandList(
        demands = listOf(
            demandMockRead("d-001"),
            demandMockRead("d-002"),
            demandMockRead("d-003"),
            demandMockRead("d-004"),
            demandMockRead("d-005"),
            demandMockRead("d-006"),
        )
    )
}

@Post("/demand/create")
fun demandCreate(): String? = handle { query: MpRequestDemandCreate ->
    MpResponseDemandCreate(
        responseId = "123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        demand = demandMockUpdate(
            id = "d-123",
            avatar = query.createData?.avatar,
            title = query.createData?.title,
            description = query.createData?.description
        )
    )
}

@Post("/demand/read")
fun demandRead(): String? = handle { query: MpRequestDemandRead ->
    MpResponseDemandRead(
        responseId = "123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        demand = demandMockRead(query.demandId ?: "")
    )
}

@Post("/demand/update")
fun demandUpdate(): String? = handle { query: MpRequestDemandUpdate ->
    MpResponseDemandUpdate(
        responseId = "123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        demand = demandMockUpdate(
            id = query.updateData?.id ?: "",
            avatar = query.updateData?.avatar,
            title = query.updateData?.title,
            description = query.updateData?.description
        )
    )
}

@Post("/demand/delete")
fun demandDelete(): String? = handle { query: MpRequestDemandDelete ->
    MpResponseDemandDelete(
        responseId = "123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        demand = demandMockRead(query.demandId ?: ""),
        deleted = true
    )
}

@Post("/demand/offers")
fun demandOffers(): String? = handle { query: MpRequestDemandOffers ->
    MpResponseDemandOffers(
        responseId = "123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        demandProposals = listOf(
            proposalMockRead("p-001"),
            proposalMockRead("p-002"),
            proposalMockRead("p-003"),
            proposalMockRead("p-004"),
        )
    )
}

@Post("/proposal/list")
fun proposalList(): String? = handle { query: MpRequestProposalList ->
    MpResponseProposalList(
        proposals = listOf(
            proposalMockRead("p-001"),
            proposalMockRead("p-002"),
            proposalMockRead("p-003"),
            proposalMockRead("p-004"),
            proposalMockRead("p-005"),
            proposalMockRead("p-006"),
        )
    )
}

@Post("/proposal/create")
fun proposalCreate(): String? = handle { query: MpRequestProposalCreate ->
    MpResponseProposalCreate(
        responseId = "123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        proposal = proposalMockUpdate(
            id = "p-123",
            avatar = query.createData?.avatar,
            title = query.createData?.title,
            description = query.createData?.description
        )
    )
}

@Post("/proposal/read")
fun proposalRead(): String? = handle { query: MpRequestProposalRead ->
    MpResponseProposalRead(
        responseId = "123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        proposal = proposalMockRead(query.proposalId ?: "")
    )
}

@Post("/proposal/update")
fun proposalUpdate(): String? = handle { query: MpRequestProposalUpdate ->
    MpResponseProposalUpdate(
        responseId = "123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        proposal = proposalMockUpdate(
            id = query.updateData?.id ?: "",
            avatar = query.updateData?.avatar,
            title = query.updateData?.title,
            description = query.updateData?.description
        )
    )
}

@Post("/proposal/delete")
fun proposalDelete(): String? = handle { query: MpRequestProposalDelete ->
    MpResponseProposalDelete(
        responseId = "123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        proposal = proposalMockRead(query.proposalId ?: ""),
        deleted = true
    )
}

@Post("/proposal/offers")
fun proposalOffers(): String? = handle { query: MpRequestProposalOffers ->
    MpResponseProposalOffers(
        responseId = "123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        proposalDemands = listOf(
            demandMockRead("d-001"),
            demandMockRead("d-002"),
            demandMockRead("d-003"),
            demandMockRead("d-004"),
        )
    )
}


@OptIn(InternalSerializationApi::class)
private inline fun <reified T : Any, reified R : Any> handle(crossinline block: suspend (T) -> R): String? =
    KotlessContext
        .HTTP
        .request
        .myBody
        ?.let { q ->
            runBlocking {
                log.debug("Handling query: {}", q)
                val query = Json.decodeFromString(T::class.serializer(), q)
                val result = block(query)
                Json.encodeToString(R::class.serializer(), result).also { r ->
                    log.debug("Sending response: {}", r)
                }
            }
        }

private fun demandMockUpdate(
    id: String,
    avatar: String?,
    title: String?,
    description: String?,
) = MpDemandDto(
    id = id,
    avatar = avatar,
    title = title,
    description = description,
    tagIds = setOf("t-001", "t-002"),
    techDets = setOf(
        TechDetsDto(
            id = "td-001",
            param = TechParamDto(
                id = "tp-001",
                name = "Tech Param 001",
                description = "Tech Param Description 001",
                priority = 0.0,
                units = setOf(demandMockUnitType("ut-001", "kg")),
            )
        )
    )
)

private fun demandMockRead(id: String) = demandMockUpdate(
    id = id,
    avatar = "icon://menu",
    title = "Demand $id",
    description = "Description of demand $id"
)

private fun demandMockUnitType(id: String, symbol: String) = UnitTypeDto(
    id = id,
    name = symbol,
    description = "Description of the $symbol",
    symbol = symbol,
    isBase = false
)

private fun proposalMockUpdate(
    id: String,
    avatar: String?,
    title: String?,
    description: String?,
) = MpProposalDto(
    id = id,
    avatar = avatar,
    title = title,
    description = description,
    tagIds = setOf("t-001", "t-002"),
    techDets = setOf(
        TechDetsDto(
            id = "td-001",
            param = TechParamDto(
                id = "tp-001",
                name = "Tech Param 001",
                description = "Tech Param Description 001",
                priority = 0.0,
                units = setOf(proposalMockUnitType("ut-001", "kg")),
            )
        )
    )
)

private fun proposalMockRead(id: String) = proposalMockUpdate(
    id = id,
    avatar = "icon://menu",
    title = "Proposal $id",
    description = "Description of proposal $id"
)

private fun proposalMockUnitType(id: String, symbol: String) = UnitTypeDto(
    id = id,
    name = symbol,
    description = "Description of the $symbol",
    symbol = symbol,
    isBase = false
)
