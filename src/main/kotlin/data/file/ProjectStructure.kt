package data.file

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager

interface ProjectStructure {

    fun findSourceRoots(): List<SourceRoot>
    fun getAllModules(): List<String>
    fun getProjectName(): String
}

class ProjectStructureImpl(private val project: Project) : ProjectStructure {

    override fun findSourceRoots() = ProjectRootManager.getInstance(project).contentSourceRoots.map { SourceRootImpl(project, it) }

    override fun getAllModules() = ModuleManager.getInstance(project).modules.map { it.name }

    override fun getProjectName() = project.name
}