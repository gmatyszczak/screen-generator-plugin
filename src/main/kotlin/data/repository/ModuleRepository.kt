package data.repository

import data.file.ProjectStructure
import model.Module
import javax.inject.Inject

interface ModuleRepository {

    fun getAllModules(): List<Module>
}

class ModuleRepositoryImpl @Inject constructor(
    private val projectStructure: ProjectStructure
) : ModuleRepository {

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
