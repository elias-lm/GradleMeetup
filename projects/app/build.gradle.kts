println("Gradle Meetup STEP 6")

plugins {
    id("build.logic.android.app")
}

dependencies {
    implementation(projects.gradleMeetup.projects.library)
    implementation(project(":projects:library"))
}

group = "com.example"

ServerFlavors {
    create("prod") {
        serverURL = ""
    }
    create("internal") {
        serverURL = ""
    }
}