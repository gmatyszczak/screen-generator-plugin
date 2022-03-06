package data.file

import data.repository.SourceRootRepository
import io.mockk.every
import io.mockk.mockk
import model.Module
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PackageExtractorImplTest {

    val module = Module("app", "app")
    val sourceRootRepositoryMock: SourceRootRepository = mockk()
    val sourceRootMock: SourceRoot = mockk()
    lateinit var packageExtractor: PackageExtractorImpl

    @BeforeEach
    fun setUp() {
        every { sourceRootRepositoryMock.findCodeSourceRoot(module) } returns sourceRootMock
    }

    @Test
    fun `when current path is null on extract from current path`() {
        packageExtractor = PackageExtractorImpl(null, sourceRootRepositoryMock)

        packageExtractor.extractFromCurrentPath() shouldBeEqualTo ""
    }

    @Test
    fun `when current path is equal to source path on extract from current path`() {
        every { sourceRootMock.path } returns "src"

        packageExtractor = PackageExtractorImpl(CurrentPath("src", true, module), sourceRootRepositoryMock)

        packageExtractor.extractFromCurrentPath() shouldBeEqualTo ""
    }

    @Test
    fun `when current path not contains source root path on extract from current path`() {
        every { sourceRootMock.path } returns "src/java"

        packageExtractor = PackageExtractorImpl(CurrentPath("src", false, module), sourceRootRepositoryMock)

        packageExtractor.extractFromCurrentPath() shouldBeEqualTo ""
    }

    @Test
    fun `when current path is directory on extract from current path`() {
        every { sourceRootMock.path } returns "src"

        packageExtractor = PackageExtractorImpl(CurrentPath("src/com/example", true, module), sourceRootRepositoryMock)

        packageExtractor.extractFromCurrentPath() shouldBeEqualTo "com.example"
    }

    @Test
    fun `when current path is file on extract from current path`() {
        every { sourceRootMock.path } returns "src"

        packageExtractor =
            PackageExtractorImpl(CurrentPath("src/com/example/test.kt", false, module), sourceRootRepositoryMock)

        packageExtractor.extractFromCurrentPath() shouldBeEqualTo "com.example"
    }
}
