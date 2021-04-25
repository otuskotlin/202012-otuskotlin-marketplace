plugins {
    kotlin("jvm")
    java
    id("org.openapi.generator")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {

    val jecksonVersion: String by project
    val validationVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jecksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jecksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("javax.validation:validation-api:$validationVersion")

    testImplementation(kotlin("test-junit"))
}

openApiGenerate {
    val basePackage = "${project.group}.transport.openapi.demand"
    packageName.set(basePackage)
    generatorName.set("kotlin-client")
    configOptions.apply {
//        put("serializationLibrary", "gson")
//        put("library", "jvm-okhttp4")
//        put("requestDateConverter", "toString")
    }
//    globalProperties.apply {
//        put("models", "")
//        put("modelDocs", "false")
//        put("invoker", "false")
//        put("apis", "false")
//    }
    inputSpec.set("${rootProject.projectDir}/specs/marketplace-all.yaml")
}

sourceSets {
    main {
        java.srcDirs("$buildDir/generate-resources/main/src/main/java")
    }
    test {
        java.srcDirs("$buildDir/generate-resources/main/src/main/java")
    }
}

tasks {
    compileKotlin.get().dependsOn(openApiGenerate)
}
