import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.container
import org.gradle.kotlin.dsl.getByType
import kotlin.properties.Delegates

class AndroidServerFlavors : Plugin<Project> {
    var androidComponents: AndroidComponentsExtension<*, *, *> by Delegates.notNull()
    var serverFlavorsContainer: NamedDomainObjectContainer<ServerFlavorExtension> by Delegates.notNull()

    override fun apply(target: Project) = with(target) {
        failIfNotExist(CommonExtension::class) {
            throw Exception("AndroidCore can be applied only if com.android.* applied")
        }

        androidComponents = extensions.getByType(AndroidComponentsExtension::class)
        serverFlavorsContainer = container(ServerFlavorExtension::class)
        extensions.add("ServerFlavors", serverFlavorsContainer)

        androidComponents.finalizeDsl {
            with(it) {
                if (serverFlavorsContainer.size > 0) {
                    flavorDimensions += listOf("server")
                    productFlavors {
                        serverFlavorsContainer.forEach { serverFlavor ->
                            create(serverFlavor.name) {
                                dimension = "server"
                                buildConfigField("String", "ServerURL", serverFlavor.serverURL)
                            }
                        }
                    }
                }
            }
        }
    }

}

abstract class ServerFlavorExtension(private val name: String) : Named {
    override fun getName(): String = name
    abstract var serverURL: String
}
