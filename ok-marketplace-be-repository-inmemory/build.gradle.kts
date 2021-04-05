val cache2kVersion: String by project

plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.cache2k:cache2k-core:$cache2kVersion")

    implementation(project(":ok-marketplace-be-common"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
