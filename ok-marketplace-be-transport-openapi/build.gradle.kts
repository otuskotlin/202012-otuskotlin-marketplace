plugins {
    kotlin("jvm")
    id("org.openapi.generator")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {

    val ktorVersion: String by project
    val logbackVersion: String by project

    implementation(kotlin("stdlib"))
//    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
//    implementation("com.squareup.moshi:moshi-kotlin:1.9.2")
//    implementation("com.squareup.moshi:moshi-adapters:1.9.2")
//    implementation("com.squareup.okhttp3:okhttp:4.2.2")

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
//    implementation("io.ktor:ktor-metrics:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation(kotlin("test-junit"))
//    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.0")

}

openApiGenerate {
    val basePackage = "${project.group}.transport.openapi.demand"
    packageName.set(basePackage)
    generatorName.set("kotlin-server")
    configOptions.apply {
//        put("library", "jvm-okhttp4")
//        put("requestDateConverter", "toString")
    }
    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
        put("invoker", "false")
        put("apis", "false")
    }
    inputSpec.set("${rootProject.projectDir}/specs/marketplace-all.yaml")
}

sourceSets.main {
    java.srcDirs("$buildDir/generate-resources/main/src/main/kotlin")
}

tasks {
    compileKotlin.get().dependsOn(openApiGenerate)
}
