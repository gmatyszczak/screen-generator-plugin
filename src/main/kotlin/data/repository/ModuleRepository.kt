package data.repository

import data.file.ProjectStructure
import model.Module

interface ModuleRepository {

    fun getAllModules(): List<Module>
}

class ModuleRepositoryImpl(private val projectStructure: ProjectStructure) : ModuleRepository {

    override fun getAllModules() =
        projectStructure.getAllModules()
            .filter { it != projectStructure.getProjectName() }
            .map {
                Module(
                    name = it,
                    nameWithoutPrefix = it.replace("${projectStructure.getProjectName()}.", "")
                )
            }
}