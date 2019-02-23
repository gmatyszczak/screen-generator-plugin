package data.repository

import data.file.ProjectStructure
import data.file.SourceRoot

interface SourceRootRepository {

    fun findCodeSourceRoot(): SourceRoot
    fun findResourcesSourceRoot(): SourceRoot
}

class SourceRootRepositoryImpl(private val projectStructure: ProjectStructure) : SourceRootRepository {

    override fun findCodeSourceRoot() =
            projectStructure.findSourceRoots().first {
                !it.path.contains("build", true)
                        && !it.path.contains("test", true)
                        && !it.path.contains("res", true)
            }

    override fun findResourcesSourceRoot() =
            projectStructure.findSourceRoots().first {
                !it.path.contains("build", true)
                        && !it.path.contains("test", true)
                        && it.path.contains("res", true)
            }
}