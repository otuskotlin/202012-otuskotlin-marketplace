rootProject.name = "otuskotlin-marketplace"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        kotlin("js") version kotlinVersion
    }
}

include("ok-marketplace-common-mp")
include("ok-marketplace-common-be")
include("ok-marketplace-frontend")
