package data.file

import com.nhaarman.mockitokotlin2.whenever
import data.repository.SourceRootRepository
import model.Module
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PackageExtractorImplTest {

    @Mock
    private lateinit var sourceRootRepositoryMock: SourceRootRepository

    @Mock
    private lateinit var sourceRootMock: SourceRoot

    private val module = Module("app", "app")

    private lateinit var packageExtractor: PackageExtractorImpl

    @Before
    fun setUp() {
        whenever(sourceRootRepositoryMock.findCodeSourceRoot(module)).thenReturn(sourceRootMock)
    }

    @Test
    fun `when current path is null on extract from current path`() {
        packageExtractor = PackageExtractorImpl(null, sourceRootRepositoryMock)

        assertEquals("", packageExtractor.extractFromCurrentPath())
    }

    @Test
    fun `when current path is equal to source path on extract from current path`() {
        whenever(sourceRootMock.path).thenReturn("src")

        packageExtractor = PackageExtractorImpl(CurrentPath("src", true, module), sourceRootRepositoryMock)

        assertEquals("", packageExtractor.extractFromCurrentPath())
    }

    @Test
    fun `when current path not contains source root path on extract from current path`() {
        whenever(sourceRootMock.path).thenReturn("src/java")

        packageExtractor = PackageExtractorImpl(CurrentPath("src", false, module), sourceRootRepositoryMock)

        assertEquals("", packageExtractor.extractFromCurrentPath())
    }

    @Test
    fun `when current path is directory on extract from current path`() {
        whenever(sourceRootMock.path).thenReturn("src")

        packageExtractor = PackageExtractorImpl(CurrentPath("src/com/example", true, module), sourceRootRepositoryMock)

        assertEquals("com.example", packageExtractor.extractFromCurrentPath())
    }

    @Test
    fun `when current path is file on extract from current path`() {
        whenever(sourceRootMock.path).thenReturn("src")

        packageExtractor = PackageExtractorImpl(CurrentPath("src/com/example/test.kt", false, module), sourceRootRepositoryMock)

        assertEquals("com.example", packageExtractor.extractFromCurrentPath())
    }
}
