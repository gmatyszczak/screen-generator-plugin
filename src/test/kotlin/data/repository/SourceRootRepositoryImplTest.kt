package data.repository

import com.nhaarman.mockitokotlin2.whenever
import data.file.ProjectStructure
import data.file.SourceRoot
import org.junit.Assert.assertEquals
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

    @InjectMocks
    private lateinit var sourceRootRepository: SourceRootRepositoryImpl

    @Test
    fun `on find code source root`() {
        whenever(sourceRootBuildMock.path).thenReturn("build")
        whenever(sourceRootTestMock.path).thenReturn("test")
        whenever(sourceRootResMock.path).thenReturn("res")
        whenever(sourceRootSrcMock.path).thenReturn("src")
        whenever(projectStructureMock.findSourceRoots()).thenReturn(listOf(sourceRootBuildMock, sourceRootTestMock, sourceRootResMock, sourceRootSrcMock))

        assertEquals(sourceRootSrcMock, sourceRootRepository.findCodeSourceRoot())
    }

    @Test
    fun `on find resources source root`() {
        whenever(sourceRootBuildMock.path).thenReturn("build")
        whenever(sourceRootTestMock.path).thenReturn("test")
        whenever(sourceRootResMock.path).thenReturn("res")
        whenever(sourceRootSrcMock.path).thenReturn("src")
        whenever(projectStructureMock.findSourceRoots()).thenReturn(listOf(sourceRootBuildMock, sourceRootTestMock, sourceRootSrcMock, sourceRootResMock))

        assertEquals(sourceRootResMock, sourceRootRepository.findResourcesSourceRoot())
    }

}