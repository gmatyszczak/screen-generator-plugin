package data.file

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import model.Module
import org.jetbrains.kotlin.idea.util.sourceRoots
import javax.inject.Inject

class ProjectStructure @Inject constructor(private val project: Project) {

    fun findSourceRoots(module: Module): List<SourceRoot> =
        ModuleManager.getInstance(project)
            .modules
            .filter { it.name.startsWith(module.name) }
            .flatMap { it.sourceRoots.toList() }
            .map { SourceRoot(project, it) }

    fun getAllModules() =
        ModuleManager.getInstance(project)
            .modules
            .filter { it.sourceRoots.isEmpty() }
            .map { it.name }

    fun getProjectName() = project.name

    fun getProjectPath() = project.basePath ?: ""
}
