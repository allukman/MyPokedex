package plugin.module

import org.gradle.api.Plugin
import org.gradle.api.Project
import util.CollectionLibs.domainDependencies
import util.alias
import util.libs

class DomainModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs.plugins.convention.android.library)
            }
            domainDependencies()
        }
    }
}