package data.repository

import com.nhaarman.mockitokotlin2.whenever
import data.file.ProjectStructure
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
    fun `on get all modules`() {
        whenever(projectStructureMock.getProjectName()).thenReturn("Application")
        whenever(projectStructureMock.getAllModules()).thenReturn(listOf("Application", "app", "domain"))

        assertEquals(listOf("app", "domain"), moduleRepository.getAllModules())
    }
}