/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 *
 * Detailed information about configuring a multi-project build in Gradle can be found
 * in the user manual at https://docs.gradle.org/7.5.1/userguide/multi_project_builds.html
 */

println("Gradle Meetup STEP 3")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    println("Gradle Meetup STEP 0")
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    includeBuild("gradle/plugins")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

plugins {
    id("settings.core")
}

rootProject.name = "GradleMeetup"

includeProjects()