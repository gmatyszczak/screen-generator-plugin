package data.repository

import data.file.ProjectStructure
import io.mockk.every
import io.mockk.mockk
import model.Module
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test

class ModuleRepositoryImplTest {

    val projectStructureMock: ProjectStructure = mockk()
    val moduleRepository = ModuleRepositoryImpl(projectStructureMock)

    @Test
    fun `when project name not included in module names on get all modules`() {
        every { projectStructureMock.getProjectName() } returns "Application"
        every { projectStructureMock.getAllModules() } returns listOf("Application", "app", "domain")

        assertEquals(
            listOf(Module("app", "app"), Module("domain", "domain")),
            moduleRepository.getAllModules()
        )
    }

    @Test
    fun `when project name  included in module names on get all modules`() {
        every { projectStructureMock.getProjectName() } returns "Application"
        every { projectStructureMock.getAllModules() } returns listOf(
            "Application",
            "Application.app",
            "Application.domain"
        )

        assertEquals(
            listOf(Module("Application.app", "app"), Module("Application.domain", "domain")),
            moduleRepository.getAllModules()
        )
    }
}
