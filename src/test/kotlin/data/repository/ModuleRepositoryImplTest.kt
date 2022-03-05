package data.repository

import com.nhaarman.mockitokotlin2.whenever
import data.file.ProjectStructure
import model.Module
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ModuleRepositoryImplTest {

    @Mock
    private lateinit var projectStructureMock: ProjectStructure

    @InjectMocks
    private lateinit var moduleRepository: ModuleRepositoryImpl

    @Test
    fun `when project name not included in module names on get all modules`() {
        whenever(projectStructureMock.getProjectName()).thenReturn("Application")
        whenever(projectStructureMock.getAllModules()).thenReturn(listOf("Application", "app", "domain"))

        assertEquals(
            listOf(Module("app", "app"), Module("domain", "domain")),
            moduleRepository.getAllModules()
        )
    }

    @Test
    fun `when project name  included in module names on get all modules`() {
        whenever(projectStructureMock.getProjectName()).thenReturn("Application")
        whenever(projectStructureMock.getAllModules()).thenReturn(listOf("Application", "Application.app", "Application.domain"))

        assertEquals(
            listOf(Module("Application.app", "app"), Module("Application.domain", "domain")),
            moduleRepository.getAllModules()
        )
    }
}
