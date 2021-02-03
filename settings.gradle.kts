
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


include("ok-marketplace-frontend")
include("ok-marketplace-kmp-transport")
include("ok-marketplace-be-common")
