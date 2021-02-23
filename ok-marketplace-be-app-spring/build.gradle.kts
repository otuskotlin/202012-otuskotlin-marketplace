import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
//    kotlin("plugin.spring") version "1.4.30"
}

group = rootProject.group
version = rootProject.group
java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    val springFuVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(kotlin("stdlib"))
    implementation("org.springframework.fu:spring-fu-autoconfigure-adapter:$springFuVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
