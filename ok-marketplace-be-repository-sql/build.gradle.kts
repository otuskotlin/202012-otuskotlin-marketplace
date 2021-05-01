plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {

    val exposedVersion: String by project
    val testContainersVersion: String by project
    val postgresDriverVersion: String by project
    val mockkVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":ok-marketplace-be-common"))
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresDriverVersion")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation(project(":ok-marketplace-be-repository-tests"))

}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}
