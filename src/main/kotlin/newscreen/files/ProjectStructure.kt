package newscreen.files

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager

interface ProjectStructure {

    fun findSourceRoots(): List<SourceRoot>
}

class ProjectStructureImpl(private val project: Project) : ProjectStructure {

    override fun findSourceRoots() = ProjectRootManager.getInstance(project).contentSourceRoots.map { SourceRootImpl(project, it) }
}