rootProject.name = "otuskotlin-marketplace"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm") version kotlinVersion
    }
}

include("ok-marketplace-common-be")
include("ok-marketplace-common-mp")
