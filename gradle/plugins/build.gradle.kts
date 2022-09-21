println("Gradle Meetup STEP 2")

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

group = "build.logic"

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}
val libsq = the<org.gradle.accessors.dm.LibrariesForLibs>()

dependencies {
    implementation(libs.android.tools.gradle)
    implementation("com.android.tools.build:gradle:7.2.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
}

gradlePlugin {
    plugins {
        create("AndroidCore") {
            group = "build.logic.android"
            id = "build.logic.android.core"
            implementationClass = "AndroidCore"
        }
        create("AndroidCoreApp") {
            group = "build.logic.android"
            id = "build.logic.android.app"
            implementationClass = "AndroidCoreApp"
        }
        create("AndroidCoreLibrary") {
            group = "build.logic.android"
            id = "build.logic.android.library"
            implementationClass = "AndroidCoreLibrary"
        }
        create("AndroidServerFlavors") {
            group = "build.logic.android"
            id = "build.logic.android.server.flavors"
            implementationClass = "AndroidServerFlavors"
        }
    }
}