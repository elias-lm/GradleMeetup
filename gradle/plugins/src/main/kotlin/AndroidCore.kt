import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.accessors.runtime.extensionOf
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.io.File

class AndroidCore : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        failIfNotExist(CommonExtension::class) {
            throw Exception("AndroidCore can be applied only if com.android.* applied")
        }

        pluginManager.apply(AndroidKotlinCore::class)
        pluginManager.apply(AndroidServerFlavors::class)

        configureIfExist(CommonExtension::class) {
            configureCommonExtension()
        }

        configureIfExist(BaseAppModuleExtension::class) {
            configureApp()
        }

        configureIfExist(LibraryExtension::class) {
            configureLibrary()
        }

        createTasks()
    }
}

fun CommonExtension<*, *, *, *>.configureCommonExtension() {
    println("AndroidCore: CommonExtension")

    compileSdk = 30

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
        create("qa") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

fun ApplicationExtension.configureApp() {
    println("AndroidCore: ApplicationExtension")

    defaultConfig {
        versionCode = 1 //TODO
        versionName = ""
        targetSdk = 30
        minSdk = 30
    }

    buildTypes {
        getByName("release") {
            applicationIdSuffix = ".release"
            versionNameSuffix = "-release"
            isDebuggable = false
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
        }
        getByName("qa") {
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            isDebuggable = true
        }
    }

}

fun LibraryExtension.configureLibrary() {
    println("AndroidCore: LibraryExtension")
}

fun Project.createTasks() {
    tasks.create("ClearGradle") {
        group = "MyTasks"
        File(rootProject.projectDir, ".gradle").run {
            if (exists()) {
                delete()
            }
        }
    }
}