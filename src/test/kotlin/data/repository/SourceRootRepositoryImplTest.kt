package data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import data.file.ProjectStructure
import data.file.SourceRoot
import model.Module
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
    private lateinit var sourceRootAndroidTestMock: SourceRoot

    @Mock
    private lateinit var sourceRootResMock: SourceRoot

    @Mock
    private lateinit var sourceRootSrcMock: SourceRoot

    @Mock
    private lateinit var sourceRootAssetsMock: SourceRoot

    @Mock
    private lateinit var sourceRootDebugResMock: SourceRoot

    private val moduleName = "presentation"
    private val module = Module("MyApplication.$moduleName", moduleName)
    private lateinit var sourceRoots: List<SourceRoot>

    @InjectMocks
    private lateinit var sourceRootRepository: SourceRootRepositoryImpl

    @Before
    fun setUp() {
        whenever(sourceRootBuildMock.path).thenReturn("/User/MyApplication/$moduleName/build/")
        whenever(sourceRootTestMock.path).thenReturn("/User/MyApplication/$moduleName/src/test")
        whenever(sourceRootAndroidTestMock.path).thenReturn("/User/MyApplication/$moduleName/src/androidTest")
        whenever(sourceRootResMock.path).thenReturn("/User/MyApplication/$moduleName/src/main/res")
        whenever(sourceRootDebugResMock.path).thenReturn("/User/MyApplication/$moduleName/src/debug/res")
        whenever(sourceRootAssetsMock.path).thenReturn("/User/MyApplication/$moduleName/src/main/assets")
        whenever(sourceRootSrcMock.path).thenReturn("/User/MyApplication/$moduleName/src/main/java")
        whenever(projectStructureMock.getProjectPath()).thenReturn("/User/MyApplication")
        sourceRoots = listOf(
            sourceRootBuildMock,
            sourceRootTestMock,
            sourceRootAndroidTestMock,
            sourceRootResMock,
            sourceRootDebugResMock,
            sourceRootAssetsMock,
            sourceRootSrcMock
        )
    }

    @Test
    fun `when source roots not empty on find code source root`() {
        whenever(projectStructureMock.findSourceRoots(module)).thenReturn(sourceRoots)

        assertEquals(sourceRootSrcMock, sourceRootRepository.findCodeSourceRoot(module))
    }

    @Test
    fun `when source roots empty on find code source root`() {
        whenever(projectStructureMock.findSourceRoots(module)).thenReturn(emptyList())

        assertEquals(null, sourceRootRepository.findCodeSourceRoot(module))
    }

    @Test
    fun `when source roots not empty on find resources source root`() {
        whenever(projectStructureMock.findSourceRoots(module)).thenReturn(sourceRoots)

        assertEquals(sourceRootResMock, sourceRootRepository.findResourcesSourceRoot(module))
    }

    @Test
    fun `when source roots empty on find resources source root`() {
        whenever(projectStructureMock.findSourceRoots(module)).thenReturn(emptyList())

        assertEquals(null, sourceRootRepository.findResourcesSourceRoot(module))
    }

    @Test
    fun `on find test code source root`() {
        whenever(projectStructureMock.findSourceRoots(module)).thenReturn(sourceRoots)

        assertEquals(sourceRootTestMock, sourceRootRepository.findCodeSourceRoot(module, "test"))
    }

    @Test
    fun `when nested module on find code source root`() {
        val nestedModuleName = "feature.module"
        val sourceRoot: SourceRoot = mock()
        whenever(sourceRoot.path).thenReturn("/User/MyApplication/feature/module/src/main/java")
        val nestedModule = Module("MyApplication.$nestedModuleName", nestedModuleName)
        whenever(projectStructureMock.findSourceRoots(nestedModule)).thenReturn(listOf(sourceRoot))

        assertEquals(sourceRoot, sourceRootRepository.findCodeSourceRoot(nestedModule, "main"))
    }
}