package data.file

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.idea.util.sourceRoots

interface ProjectStructure {

    fun findSourceRoots(module: String): List<SourceRoot>
    fun getAllModules(): List<String>
    fun getProjectName(): String
    fun getProjectPath(): String
}

class ProjectStructureImpl(private val project: Project) : ProjectStructure {

    override fun findSourceRoots(module: String) =
            ModuleManager.getInstance(project).findModuleByName(module)?.sourceRoots?.map { SourceRootImpl(project, it) }
                    ?: throw IllegalStateException("$module module doesn't exist!")

    override fun getAllModules() = ModuleManager.getInstance(project).modules.map { it.name }

    override fun getProjectName() = project.name

    override fun getProjectPath() = project.basePath ?: ""
}