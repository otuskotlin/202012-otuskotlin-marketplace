plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {

    val kotestVersion: String by project
    val mockkVersion: String by project

    implementation(kotlin("stdlib"))
    implementation(project(":ok-marketplace-be-common"))

    api("io.kotest:kotest-runner-junit5:$kotestVersion")
    api("io.mockk:mockk:$mockkVersion")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}
