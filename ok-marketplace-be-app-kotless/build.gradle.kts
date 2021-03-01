import io.kotless.plugin.gradle.dsl.kotless

plugins {
    kotlin("jvm")
    id("io.kotless")
    kotlin("plugin.serialization")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    val kotlessVersion: String by project
    val coroutinesVersion: String by project

    implementation(project(":ok-marketplace-be-common"))
    implementation(project(":ok-marketplace-mp-transport-mp"))
    implementation(project(":ok-marketplace-be-mappers-mp"))

    implementation(kotlin("stdlib"))
//    implementation("io.kotless", "lang", kotlessVersion)
    implementation("io.kotless", "kotless-lang", kotlessVersion)
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

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
        lambda {
            runtime = io.kotless.resource.Lambda.Config.Runtime.GraalVM
        }
    }

    extensions {
        local {
            //enable AWS emulation (disabled by default)
            useAWSEmulation = true
        }
    }
}
