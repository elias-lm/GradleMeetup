import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class AndroidCoreLibrary : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("android.library.helper.plugin")
        pluginManager.apply(AndroidCore::class)

    }
}

