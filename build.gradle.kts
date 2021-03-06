plugins {
    kotlin("multiplatform") apply false
    kotlin("jvm") apply false
    kotlin("js") apply false

    id("org.openapi.generator") apply false
    id("com.bmuschko.docker-java-application") apply false
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
        maven { url = uri("https://repo.spring.io/milestone") }
    }

}
