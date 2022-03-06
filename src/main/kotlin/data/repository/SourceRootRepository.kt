package data.repository

import data.file.ProjectStructure
import data.file.SourceRoot
import model.DEFAULT_SOURCE_SET
import model.Module
import javax.inject.Inject

interface SourceRootRepository {

    fun findCodeSourceRoot(module: Module, sourceSet: String = DEFAULT_SOURCE_SET): SourceRoot?
    fun findResourcesSourceRoot(module: Module): SourceRoot?
}

class SourceRootRepositoryImpl @Inject constructor(
    private val projectStructure: ProjectStructure
) : SourceRootRepository {

    override fun findCodeSourceRoot(module: Module, sourceSet: String) =
        projectStructure.findSourceRoots(module).firstOrNull {
            val pathTrimmed = it.path.removeModulePathPrefix(module)
            pathTrimmed.contains("src", true) &&
                pathTrimmed.contains(sourceSet) &&
                !pathTrimmed.contains("assets", true) &&
                !pathTrimmed.contains("res", true)
        }

    override fun findResourcesSourceRoot(module: Module) =
        projectStructure.findSourceRoots(module).firstOrNull {
            val pathTrimmed = it.path.removeModulePathPrefix(module)
            pathTrimmed.contains("src", true) &&
                pathTrimmed.contains("main", true) &&
                pathTrimmed.contains("res", true)
        }

    private fun String.removeModulePathPrefix(module: Module) =
        removePrefix(projectStructure.getProjectPath() + "/" + module.nameWithoutPrefix.replace(".", "/"))
}
