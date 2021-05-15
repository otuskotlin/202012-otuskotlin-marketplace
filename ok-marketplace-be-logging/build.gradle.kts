val kotlinVersion: String by project
val logbackVersion: String by project
val logbackEncoderVersion: String by project
val logbackKafkaVersion: String by project

plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    // logback
    implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")
    implementation("com.github.danielwegener:logback-kafka-appender:$logbackKafkaVersion")
    api("ch.qos.logback:logback-classic:$logbackVersion")
}
