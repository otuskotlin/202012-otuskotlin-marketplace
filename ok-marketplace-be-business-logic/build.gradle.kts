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

    implementation(kotlin("stdlib"))
    implementation(project(":ok-marketplace-mp-common"))
    implementation(project(":ok-marketplace-be-common"))
    implementation(project(":ok-marketplace-mp-pipelines"))
    implementation(project(":ok-marketplace-mp-pipelines-validation"))

    testImplementation(kotlin("test-junit5"))
    testImplementation(platform("org.junit:junit-bom:5.7.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
