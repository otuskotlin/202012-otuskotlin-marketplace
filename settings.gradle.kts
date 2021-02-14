
rootProject.name = "otuskotlin-marketplace"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
        kotlin("js") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
    }
}

include("ok-marketplace-common-mp")
include("ok-marketplace-common-be")
include("ok-marketplace-frontend")
include("ok-marketplace-transport-mp")
include("ok-marketplace-common-be")
