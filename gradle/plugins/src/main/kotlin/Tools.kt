import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import java.io.File
import kotlin.reflect.KClass

fun Settings.includeProjects(projectsFolderName: String = "projects") {

    fun recursiveProjectFinder(file: File, projectName: String): List<String> {
        return (file.listFiles() ?: arrayOf())
            .filter { it.name != "buildSrc" }
            .filter { it.listFiles()?.contains(File(it, ".ignoreModule")) == false }
            .flatMap { childFile ->
                val name = "$projectName:${childFile.name}"
                if (File(childFile, "build.gradle.kts").exists()) {
                    println("--> $name")
                    listOf(name)
                } else {
                    recursiveProjectFinder(childFile, name)
                }
            }
    }

    recursiveProjectFinder(File("${rootDir}/$projectsFolderName"), projectsFolderName)
        .forEach { include(it) }

}

fun <T : Any> Project.configureIfExist(type: KClass<T>, configure: T.() -> Unit) {
    if (extensions.findByType(type) != null) {
        extensions.configure(type, configure)
    }
}

fun <T : Any> Project.failIfNotExist(type: KClass<T>, onFail: () -> Unit) {
    if (extensions.findByType(type) == null) {
        onFail()
    }
}