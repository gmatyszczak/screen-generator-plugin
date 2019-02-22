package data.repository

import data.file.ProjectStructure
import data.file.SourceRoot

interface SourceRootRepository {

    fun findFirstModuleSourceRoot(): SourceRoot
}

class SourceRootRepositoryImpl(private val projectStructure: ProjectStructure) : SourceRootRepository {

    override fun findFirstModuleSourceRoot() =
            projectStructure.findSourceRoots().first {
                !it.path.contains("build", true)
                        && !it.path.contains("test", true)
                        && !it.path.contains("res", true)
            }
}