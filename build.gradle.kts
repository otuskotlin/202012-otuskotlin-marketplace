plugins {
    kotlin("multiplatform") apply false
}

group = "ru.otus.otuskotlin.marketplace"
version = "0.0.1"

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        jcenter()
        mavenCentral()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
    }

}
