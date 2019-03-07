package data.repository

import data.file.ProjectStructure
import data.file.SourceRoot

interface SourceRootRepository {

    fun findCodeSourceRoot(module: String): SourceRoot
    fun findResourcesSourceRoot(module: String): SourceRoot
}

class SourceRootRepositoryImpl(private val projectStructure: ProjectStructure) : SourceRootRepository {

    override fun findCodeSourceRoot(module: String) =
            projectStructure.findSourceRoots(module).first {
                !it.path.contains("build", true)
                        && !it.path.contains("test", true)
                        && !it.path.contains("res", true)
            }

    override fun findResourcesSourceRoot(module: String) =
            projectStructure.findSourceRoots(module).first {
                !it.path.contains("build", true)
                        && !it.path.contains("test", true)
                        && it.path.contains("res", true)
            }
}