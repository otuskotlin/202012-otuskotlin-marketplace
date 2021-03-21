import io.kotless.plugin.gradle.dsl.kotless
import io.kotless.resource.Lambda.Config.Runtime

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

    implementation(project(":ok-marketplace-mp-common"))
    implementation(project(":ok-marketplace-be-common"))
    implementation(project(":ok-marketplace-mp-transport-mp"))
    implementation(project(":ok-marketplace-be-mappers-mp"))
    implementation(project(":ok-marketplace-be-business-logic"))

    implementation(kotlin("stdlib"))
    implementation("io.kotless", "kotless-lang", kotlessVersion)
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

}

kotless {
    config {
        bucket = "com.crowdproj.marketplace"
        dsl {
            type = io.kotless.DSLType.Kotless
        }
        terraform {
            profile = "default"
            region = "us-east-1"
        }
        optimization {
            //default config
            autowarm = io.kotless.plugin.gradle.dsl.KotlessConfig.Optimization.Autowarm(enable = false, minutes = -1)
        }
    }
    webapp {
        route53 = io.kotless.plugin.gradle.dsl.Webapp.Route53("marketplace", "crowdproj.com","crowdproj.com")
        lambda {
            memoryMb = 256
            runtime = Runtime.Java11
        }
    }

    extensions {
        local {
            //enable AWS emulation (disabled by default)
            useAWSEmulation = true
        }
        terraform {
            allowDestroy = true
        }
    }
}
