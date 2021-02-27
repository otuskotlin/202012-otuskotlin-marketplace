rootProject.name = "otuskotlin-marketplace"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val springDependencyVersion: String by settings
    val springBootVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        kotlin("js") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion

        id("org.openapi.generator") version openapiVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyVersion
        id("com.bmuschko.docker-java-application") version bmuschkoVersion
    }
}

// Мультиплатформенные подпроекты
include("ok-marketplace-common-mp")
include("ok-marketplace-mp-transport-mp")

// Фронтенд подпроекты
include("ok-marketplace-fe-app")
include("ok-marketplace-fe-common")
include("ok-marketplace-fe-mappers-mp")

// Бэкенд (JVM) подпроекты
include("ok-marketplace-be-common")
include("ok-marketplace-be-mappers-mp")
include("ok-marketplace-transport-openapi-demand-kt")
include("ok-marketplace-transport-openapi-demandoffers-kt")
include("ok-marketplace-transport-openapi-proposal-kt")
include("ok-marketplace-transport-openapi-proposaloffers-kt")

// Приложения
include("ok-marketplace-be-app-spring")
include("ok-marketplace-be-app-ktor")
