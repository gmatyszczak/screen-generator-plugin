package data.repository

import data.file.ProjectStructure
import data.file.SourceRoot
import model.Module

interface SourceRootRepository {

    fun findCodeSourceRoot(module: Module): SourceRoot?
    fun findResourcesSourceRoot(module: Module): SourceRoot
}

class SourceRootRepositoryImpl(private val projectStructure: ProjectStructure) : SourceRootRepository {

    override fun findCodeSourceRoot(module: Module) =
            projectStructure.findSourceRoots(module).firstOrNull {
                val pathTrimmed = it.path.removeModulePathPrefix(module)
                pathTrimmed.contains("src", true)
                        && pathTrimmed.contains("main", true)
                        && !pathTrimmed.contains("assets", true)
                        && !pathTrimmed.contains("test", true)
                        && !pathTrimmed.contains("res", true)
            }

    override fun findResourcesSourceRoot(module: Module) =
            projectStructure.findSourceRoots(module).first {
                val pathTrimmed = it.path.removeModulePathPrefix(module)
                pathTrimmed.contains("src", true)
                        && pathTrimmed.contains("main", true)
                        && pathTrimmed.contains("res", true)
            }

    private fun String.removeModulePathPrefix(module: Module) =
            removePrefix(projectStructure.getProjectPath() + "/" + module.nameWithoutPrefix)
}