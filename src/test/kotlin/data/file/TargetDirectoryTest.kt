package data.file

import data.repository.SourceRootRepository
import io.mockk.every
import io.mockk.mockk
import model.FileType
import model.Module
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

internal class TargetDirectoryTest {

    val module: Module = mockk()
    val directory: Directory = mockk()
    val sourceRoot: SourceRoot = mockk {
        every { directory } returns this@TargetDirectoryTest.directory
    }
    val sourceRootRepository: SourceRootRepository = mockk()
    val targetDirectory = TargetDirectory(sourceRootRepository)

    @Test
    fun `when file type kotlin and parent directory exists and subdirectory exists`() {
        val screenElement: ScreenElement = mockk {
            every { fileType } returns FileType.KOTLIN
            every { sourceSet } returns "main"
            every { subdirectory } returns "sub1/sub2"
        }
        every { sourceRootRepository.findCodeSourceRoot(module, "main") } returns sourceRoot
        every { directory.findSubdirectory("abc") } returns directory
        every { directory.findSubdirectory("def") } returns directory
        every { directory.findSubdirectory("sub1") } returns directory
        every { directory.findSubdirectory("sub2") } returns directory

        targetDirectory.findOrCreate(screenElement, module, "abc.def") shouldBeEqualTo directory
    }

    @Test
    fun `when file type kotlin and parent directory does not exists and subdirectory does not exists`() {
        val screenElement: ScreenElement = mockk {
            every { fileType } returns FileType.KOTLIN
            every { sourceSet } returns "main"
            every { subdirectory } returns "sub"
        }
        every { sourceRootRepository.findCodeSourceRoot(module, "main") } returns sourceRoot
        every { directory.findSubdirectory("abc") } returns null
        every { directory.createSubdirectory("abc") } returns directory
        every { directory.findSubdirectory("sub") } returns null
        every { directory.createSubdirectory("sub") } returns directory

        targetDirectory.findOrCreate(screenElement, module, "abc") shouldBeEqualTo directory
    }

    @Test
    fun `when file type kotlin and parent directory does exists and subdirectory empty`() {
        val screenElement: ScreenElement = mockk {
            every { fileType } returns FileType.KOTLIN
            every { sourceSet } returns "main"
            every { subdirectory } returns ""
        }
        every { sourceRootRepository.findCodeSourceRoot(module, "main") } returns sourceRoot
        every { directory.findSubdirectory("abc") } returns directory

        targetDirectory.findOrCreate(screenElement, module, "abc") shouldBeEqualTo directory
    }

    @Test
    fun `when file type layout xml and parent directory does exists and subdirectory does exist`() {
        val screenElement: ScreenElement = mockk {
            every { fileType } returns FileType.LAYOUT_XML
            every { sourceSet } returns "main"
            every { subdirectory } returns "sub"
        }
        every { sourceRootRepository.findResourcesSourceRoot(module) } returns sourceRoot
        every { directory.findSubdirectory("layout") } returns directory
        every { directory.findSubdirectory("sub") } returns directory

        targetDirectory.findOrCreate(screenElement, module, "abc") shouldBeEqualTo directory
    }

    @Test
    fun `when file type layout xml and parent directory does not exists and subdirectory does not exist`() {
        val screenElement: ScreenElement = mockk {
            every { fileType } returns FileType.LAYOUT_XML
            every { sourceSet } returns "main"
            every { subdirectory } returns "sub"
        }
        every { sourceRootRepository.findResourcesSourceRoot(module) } returns sourceRoot
        every { directory.findSubdirectory("layout") } returns null
        every { directory.createSubdirectory("layout") } returns directory
        every { directory.findSubdirectory("sub") } returns null
        every { directory.createSubdirectory("sub") } returns directory

        targetDirectory.findOrCreate(screenElement, module, "abc") shouldBeEqualTo directory
    }

    @Test
    fun `when file type layout xml and parent directory exists and subdirectory empty`() {
        val screenElement: ScreenElement = mockk {
            every { fileType } returns FileType.LAYOUT_XML
            every { sourceSet } returns "main"
            every { subdirectory } returns ""
        }
        every { sourceRootRepository.findResourcesSourceRoot(module) } returns sourceRoot
        every { directory.findSubdirectory("layout") } returns directory

        targetDirectory.findOrCreate(screenElement, module, "abc") shouldBeEqualTo directory
    }
}