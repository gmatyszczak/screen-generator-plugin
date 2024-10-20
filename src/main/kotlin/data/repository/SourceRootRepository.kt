package data.repository

import data.file.ProjectStructure
import model.DEFAULT_SOURCE_SET
import model.Module
import javax.inject.Inject

class SourceRootRepository @Inject constructor(
    private val projectStructure: ProjectStructure
) {

    fun findCodeSourceRoot(module: Module, sourceSet: String = DEFAULT_SOURCE_SET) =
        projectStructure.findSourceRoots(module).firstOrNull {
            val pathTrimmed = it.path.removeModulePathPrefix(module)
            pathTrimmed.contains("src", true) &&
                pathTrimmed.contains(sourceSet) && (
                pathTrimmed.contains("java", true) ||
                    pathTrimmed.contains("kotlin", true)
                )
        }

    fun findResourcesSourceRoot(module: Module) =
        projectStructure.findSourceRoots(module).firstOrNull {
            val pathTrimmed = it.path.removeModulePathPrefix(module)
            pathTrimmed.contains("src", true) &&
                pathTrimmed.contains("main", true) &&
                pathTrimmed.contains("res", true)
        }

    private fun String.removeModulePathPrefix(module: Module) =
        removePrefix(projectStructure.getProjectPath() + "/" + module.nameWithoutPrefix.replace(".", "/"))
}
