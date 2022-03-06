package data.repository

import data.file.ProjectStructure
import data.file.SourceRoot
import io.mockk.every
import io.mockk.mockk
import model.Module
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SourceRootRepositoryImplTest {

    val projectStructureMock: ProjectStructure = mockk()
    val sourceRootBuildMock: SourceRoot = mockk()
    val sourceRootTestMock: SourceRoot = mockk()
    val sourceRootAndroidTestMock: SourceRoot = mockk()
    val sourceRootResMock: SourceRoot = mockk()
    val sourceRootSrcMock: SourceRoot = mockk()
    val sourceRootAssetsMock: SourceRoot = mockk()
    val sourceRootDebugResMock: SourceRoot = mockk()
    val moduleName = "presentation"
    val module = Module("MyApplication.$moduleName", moduleName)
    lateinit var sourceRoots: List<SourceRoot>
    val sourceRootRepository = SourceRootRepositoryImpl(projectStructureMock)

    @BeforeEach
    fun setUp() {
        every { sourceRootBuildMock.path } returns "/User/MyApplication/$moduleName/build/"
        every { sourceRootTestMock.path } returns "/User/MyApplication/$moduleName/src/test"
        every { sourceRootAndroidTestMock.path } returns "/User/MyApplication/$moduleName/src/androidTest"
        every { sourceRootResMock.path } returns "/User/MyApplication/$moduleName/src/main/res"
        every { sourceRootDebugResMock.path } returns "/User/MyApplication/$moduleName/src/debug/res"
        every { sourceRootAssetsMock.path } returns "/User/MyApplication/$moduleName/src/main/assets"
        every { sourceRootSrcMock.path } returns "/User/MyApplication/$moduleName/src/main/java"
        every { projectStructureMock.getProjectPath() } returns "/User/MyApplication"
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
        every { projectStructureMock.findSourceRoots(module) } returns sourceRoots

        assertEquals(sourceRootSrcMock, sourceRootRepository.findCodeSourceRoot(module))
    }

    @Test
    fun `when source roots empty on find code source root`() {
        every { projectStructureMock.findSourceRoots(module) } returns emptyList()

        assertEquals(null, sourceRootRepository.findCodeSourceRoot(module))
    }

    @Test
    fun `when source roots not empty on find resources source root`() {
        every { projectStructureMock.findSourceRoots(module) } returns sourceRoots

        assertEquals(sourceRootResMock, sourceRootRepository.findResourcesSourceRoot(module))
    }

    @Test
    fun `when source roots empty on find resources source root`() {
        every { projectStructureMock.findSourceRoots(module) } returns emptyList()

        assertEquals(null, sourceRootRepository.findResourcesSourceRoot(module))
    }

    @Test
    fun `on find test code source root`() {
        every { projectStructureMock.findSourceRoots(module) } returns sourceRoots

        assertEquals(sourceRootTestMock, sourceRootRepository.findCodeSourceRoot(module, "test"))
    }

    @Test
    fun `when nested module on find code source root`() {
        val nestedModuleName = "feature.module"
        val sourceRoot: SourceRoot = mockk()
        every { sourceRoot.path } returns "/User/MyApplication/feature/module/src/main/java"
        val nestedModule = Module("MyApplication.$nestedModuleName", nestedModuleName)
        every { projectStructureMock.findSourceRoots(nestedModule) } returns listOf(sourceRoot)

        assertEquals(sourceRoot, sourceRootRepository.findCodeSourceRoot(nestedModule, "main"))
    }
}
