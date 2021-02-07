rootProject.name = "otuskotlin-marketplace"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        kotlin("js") version kotlinVersion

        id("org.openapi.generator") version openapiVersion
    }
}

include("ok-marketplace-common-mp")
include("ok-marketplace-common-be")
include("ok-marketplace-transport-openapi-demand-kt")
include("ok-marketplace-transport-openapi-demandoffers-kt")
include("ok-marketplace-transport-openapi-proposal-kt")
include("ok-marketplace-transport-openapi-proposaloffers-kt")
