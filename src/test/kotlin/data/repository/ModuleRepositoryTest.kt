package data.repository

import data.file.ProjectStructure
import io.mockk.every
import io.mockk.mockk
import model.Module
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class ModuleRepositoryTest {

    val projectStructureMock: ProjectStructure = mockk()
    val moduleRepository = ModuleRepository(projectStructureMock)

    @Test
    fun `when project name not included in module names on get all modules`() {
        every { projectStructureMock.getProjectName() } returns "Application"
        every { projectStructureMock.getAllModules() } returns listOf("Application", "app", "domain")

        moduleRepository.getAllModules() shouldBeEqualTo listOf(
            Module("app", "app"),
            Module("domain", "domain")
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

        moduleRepository.getAllModules() shouldBeEqualTo listOf(
            Module("Application.app", "app"),
            Module("Application.domain", "domain")
        )
    }
}
