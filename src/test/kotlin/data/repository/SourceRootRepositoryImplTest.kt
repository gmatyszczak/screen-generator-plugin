package data.repository

import com.nhaarman.mockitokotlin2.whenever
import data.file.ProjectStructure
import data.file.SourceRoot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SourceRootRepositoryImplTest {

    @Mock
    private lateinit var projectStructureMock: ProjectStructure

    @Mock
    private lateinit var sourceRootBuildMock: SourceRoot

    @Mock
    private lateinit var sourceRootTestMock: SourceRoot

    @Mock
    private lateinit var sourceRootResMock: SourceRoot

    @Mock
    private lateinit var sourceRootSrcMock: SourceRoot

    private val moduleName = "presentation"

    @InjectMocks
    private lateinit var sourceRootRepository: SourceRootRepositoryImpl

    @Before
    fun setUp() {
        whenever(sourceRootBuildMock.path).thenReturn("/User/MyApplication/$moduleName/build")
        whenever(sourceRootTestMock.path).thenReturn("/User/MyApplication/$moduleName/test")
        whenever(sourceRootResMock.path).thenReturn("/User/MyApplication/$moduleName/res")
        whenever(sourceRootSrcMock.path).thenReturn("/User/MyApplication/$moduleName/src")
        whenever(projectStructureMock.findSourceRoots(moduleName)).thenReturn(listOf(sourceRootBuildMock, sourceRootTestMock, sourceRootResMock, sourceRootSrcMock))
        whenever(projectStructureMock.getProjectPath()).thenReturn("/User/MyApplication")
    }

    @Test
    fun `on find code source root`() {
        assertEquals(sourceRootSrcMock, sourceRootRepository.findCodeSourceRoot(moduleName))
    }

    @Test
    fun `on find resources source root`() {
        assertEquals(sourceRootResMock, sourceRootRepository.findResourcesSourceRoot(moduleName))
    }

}