package data.repository

import data.file.ProjectStructure
import data.file.SourceRoot
import io.mockk.every
import io.mockk.mockk
import model.Module
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SourceRootRepositoryTest {

    val projectStructureMock: ProjectStructure = mockk()
    val sourceRootBuildMock: SourceRoot = mockk()
    val sourceRootTestJavaMock: SourceRoot = mockk()
    val sourceRootTestKotlinMock: SourceRoot = mockk()
    val sourceRootAndroidTestMock: SourceRoot = mockk()
    val sourceRootResMock: SourceRoot = mockk()
    val sourceRootSrcJavaMock: SourceRoot = mockk()
    val sourceRootSrcKotlinMock: SourceRoot = mockk()
    val sourceRootAssetsMock: SourceRoot = mockk()
    val sourceRootDebugResMock: SourceRoot = mockk()
    val sourceRootBaselineProfilesMock: SourceRoot = mockk()
    val moduleName = "presentation"
    val module = Module("MyApplication.$moduleName", moduleName)
    val baseSourceRoots = listOf(
        sourceRootBuildMock,
        sourceRootAndroidTestMock,
        sourceRootResMock,
        sourceRootDebugResMock,
        sourceRootAssetsMock,
        sourceRootBaselineProfilesMock,
    )
    val javaSourceRoots = baseSourceRoots + listOf(sourceRootSrcJavaMock, sourceRootTestJavaMock)
    val kotlinSourceRoots = baseSourceRoots + listOf(sourceRootSrcKotlinMock, sourceRootTestKotlinMock)
    val sourceRootRepository = SourceRootRepository(projectStructureMock)

    @BeforeEach
    fun setUp() {
        every { sourceRootBuildMock.path } returns "/User/MyApplication/$moduleName/build/"
        every { sourceRootTestJavaMock.path } returns "/User/MyApplication/$moduleName/src/test/java"
        every { sourceRootTestKotlinMock.path } returns "/User/MyApplication/$moduleName/src/test/kotlin"
        every { sourceRootAndroidTestMock.path } returns "/User/MyApplication/$moduleName/src/androidTest"
        every { sourceRootResMock.path } returns "/User/MyApplication/$moduleName/src/main/res"
        every { sourceRootDebugResMock.path } returns "/User/MyApplication/$moduleName/src/debug/res"
        every { sourceRootAssetsMock.path } returns "/User/MyApplication/$moduleName/src/main/assets"
        every { sourceRootBaselineProfilesMock.path } returns "/User/MyApplication/$moduleName/src/main/baselineProfiles"
        every { sourceRootSrcJavaMock.path } returns "/User/MyApplication/$moduleName/src/main/java"
        every { sourceRootSrcKotlinMock.path } returns "/User/MyApplication/$moduleName/src/main/kotlin"
        every { projectStructureMock.getProjectPath() } returns "/User/MyApplication"
    }

    fun sourceRootsParameters() =
        listOf(
            arguments(javaSourceRoots, sourceRootSrcJavaMock),
            arguments(kotlinSourceRoots, sourceRootSrcKotlinMock),
        )

    fun testSourceRootsParameters() =
        listOf(
            arguments(javaSourceRoots, sourceRootTestJavaMock),
            arguments(kotlinSourceRoots, sourceRootTestKotlinMock),
        )

    @MethodSource("sourceRootsParameters")
    @ParameterizedTest
    fun `when source roots not empty on find code source root`(
        sourceRoots: List<SourceRoot>,
        codeSourceRoot: SourceRoot,
    ) {
        every { projectStructureMock.findSourceRoots(module) } returns sourceRoots

        sourceRootRepository.findCodeSourceRoot(module) shouldBeEqualTo codeSourceRoot
    }

    @Test
    fun `when source roots empty on find code source root`() {
        every { projectStructureMock.findSourceRoots(module) } returns emptyList()

        sourceRootRepository.findCodeSourceRoot(module) shouldBeEqualTo null
    }

    @Test
    fun `when source roots not empty on find resources source root`() {
        every { projectStructureMock.findSourceRoots(module) } returns javaSourceRoots

        sourceRootRepository.findResourcesSourceRoot(module) shouldBeEqualTo sourceRootResMock
    }

    @Test
    fun `when source roots empty on find resources source root`() {
        every { projectStructureMock.findSourceRoots(module) } returns emptyList()

        sourceRootRepository.findResourcesSourceRoot(module) shouldBeEqualTo null
    }

    @MethodSource("testSourceRootsParameters")
    @ParameterizedTest
    fun `on find test code source root`(
        sourceRoots: List<SourceRoot>,
        codeSourceRoot: SourceRoot,
    ) {
        every { projectStructureMock.findSourceRoots(module) } returns sourceRoots

        sourceRootRepository.findCodeSourceRoot(module, "test") shouldBeEqualTo codeSourceRoot
    }

    @Test
    fun `when nested module on find code source root`() {
        val nestedModuleName = "feature.module"
        val sourceRoot: SourceRoot = mockk()
        every { sourceRoot.path } returns "/User/MyApplication/feature/module/src/main/java"
        val nestedModule = Module("MyApplication.$nestedModuleName", nestedModuleName)
        every { projectStructureMock.findSourceRoots(nestedModule) } returns listOf(sourceRoot)

        sourceRootRepository.findCodeSourceRoot(nestedModule, "main") shouldBeEqualTo sourceRoot
    }
}
