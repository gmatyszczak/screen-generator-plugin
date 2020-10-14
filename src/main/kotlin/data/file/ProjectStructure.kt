package data.file

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import model.Module
import org.jetbrains.kotlin.idea.util.sourceRoots

interface ProjectStructure {

    fun findSourceRoots(module: Module): List<SourceRoot>
    fun getAllModules(): List<String>
    fun getProjectName(): String
    fun getProjectPath(): String
}

class ProjectStructureImpl(private val project: Project) : ProjectStructure {

    override fun findSourceRoots(module: Module) =
            ModuleManager.getInstance(project).findModuleByName(module.name)?.sourceRoots?.map { SourceRootImpl(project, it) }
                    ?: throw IllegalStateException("${module.name} module doesn't exist!")

    override fun getAllModules() = ModuleManager.getInstance(project).modules.map { it.name }

    override fun getProjectName() = project.name

    override fun getProjectPath() = project.basePath ?: ""
}