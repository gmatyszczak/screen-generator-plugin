package data.repository

import data.file.ProjectStructure
import model.Module
import javax.inject.Inject

class ModuleRepository @Inject constructor(
    private val projectStructure: ProjectStructure
) {

    fun getAllModules() =
        projectStructure.getAllModules()
            .filter { it != projectStructure.getProjectName() }
            .map {
                Module(
                    name = it,
                    nameWithoutPrefix = it.replace("${projectStructure.getProjectName()}.", "")
                )
            }
}
