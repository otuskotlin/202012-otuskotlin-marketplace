import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.serialization")
//    kotlin("plugin.spring") version "1.4.30"
}

group = rootProject.group
version = rootProject.group
java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    val springFuVersion: String by project
    val serializationVersion: String by project

    implementation(project(":ok-marketplace-common-mp"))
    implementation(project(":ok-marketplace-mp-transport-mp"))
    implementation(project(":ok-marketplace-be-common"))
    implementation(project(":ok-marketplace-be-mappers-mp"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(kotlin("stdlib"))

    implementation("org.springframework.fu:spring-fu-kofu:$springFuVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("org.springframework:spring-webmvc")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
