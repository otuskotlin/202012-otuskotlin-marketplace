rootProject.name = "otuskotlin-marketplace"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val springDependencyVersion: String by settings
    val springBootVersion: String by settings
    val bmuschkoVersion: String by settings
    val kotlessVersion: String by settings

    plugins {
        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        kotlin("js") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion

        id("org.openapi.generator") version openapiVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyVersion
        id("com.bmuschko.docker-java-application") version bmuschkoVersion
        id("io.kotless") version kotlessVersion apply false
    }
}

// Мультиплатформенные подпроекты
include("ok-marketplace-mp-common")
include("ok-marketplace-mp-transport-mp")
include("ok-marketplace-mp-pipelines")

// Фронтенд подпроекты
include("ok-marketplace-fe-app-kreact")
include("ok-marketplace-fe-common")
include("ok-marketplace-fe-mappers-mp")

// Бэкенд (JVM) подпроекты
include("ok-marketplace-be-common")
include("ok-marketplace-be-mappers-mp")
include("ok-marketplace-be-transport-openapi")
include("ok-marketplace-be-mappers-openapi")

// Приложения
include("ok-marketplace-be-app-spring")
include("ok-marketplace-be-app-ktor")
include("ok-marketplace-be-app-kotless")
