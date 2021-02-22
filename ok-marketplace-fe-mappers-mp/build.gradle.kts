plugins {
    kotlin("js")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(project(":ok-marketplace-common-mp"))
    implementation(project(":ok-marketplace-fe-common"))
    implementation(project(":ok-marketplace-mp-transport-mp"))

}

kotlin {
    js {
        browser {
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
        binaries.executable()
    }
    js {
        nodejs {
        }
        binaries.executable()
    }
}
