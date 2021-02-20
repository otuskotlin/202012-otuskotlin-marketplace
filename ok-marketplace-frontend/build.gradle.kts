plugins {
    kotlin("js")
}

dependencies {
    testImplementation(kotlin("test-js"))
    val wrapperVersion = "pre.141"
    val reactVersion = "17.0.1-$wrapperVersion"
    val routerVersion ="5.2.0-$wrapperVersion"
    val kotlinVersion = "1.4.21"
    val kotlinJsCoroutines = "1.4.2"
    val ktMaterialWrapper = "0.6.2"

    implementation("org.jetbrains:kotlin-react:$reactVersion-kotlin-$kotlinVersion")
    implementation("org.jetbrains:kotlin-react-dom:$reactVersion-kotlin-$kotlinVersion")
    implementation("org.jetbrains:kotlin-styled:$routerVersion-kotlin-$kotlinVersion")
    implementation("org.jetbrains:kotlin-react-router-dom:$routerVersion-kotlin-$kotlinVersion")
    implementation("com.ccfraser.muirwik:muirwik-components:$ktMaterialWrapper")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinJsCoroutines")
}

kotlin {
    js(LEGACY) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
}
