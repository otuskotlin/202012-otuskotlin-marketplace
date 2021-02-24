import io.kotless.plugin.gradle.dsl.kotless

plugins {
    kotlin("jvm")
    id("io.kotless")
//    id("io.kotless") version "0.1.7-beta-5"
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    val kotlessVersion: String by project

    implementation(kotlin("stdlib"))
//    implementation("io.kotless", "lang", kotlessVersion)
    implementation("io.kotless", "kotless-lang", kotlessVersion)
}

kotless {
    config {
        bucket = "kotless.s3.example.com"
        dsl {
            type = io.kotless.DSLType.Kotless
        }
        terraform {
            profile = "example"
            region = "us-east-1"
        }
    }
    webapp {
        //Optional parameter, by default technical name will be generated
        route53 = io.kotless.plugin.gradle.dsl.Webapp.Route53("kotless", "example.com")
    }
}
