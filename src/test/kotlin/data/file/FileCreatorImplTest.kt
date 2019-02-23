package data.file

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import data.repository.SettingsRepository
import data.repository.SourceRootRepository
import model.FileType
import model.ScreenElement
import model.Settings
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FileCreatorImplTest {

    @Mock
    private lateinit var settingsRepositoryMock: SettingsRepository

    @Mock
    private lateinit var codeSourceRootMock: SourceRoot

    @Mock
    private lateinit var resourcesSourceRootMock: SourceRoot

    @Mock
    private lateinit var codeDirectoryMock: Directory

    @Mock
    private lateinit var resourcesDirectoryMock: Directory

    @Mock
    private lateinit var sourceRootRepositoryMock: SourceRootRepository

    @InjectMocks
    private lateinit var fileCreator: FileCreatorImpl

    private val testTemplate = "data class %name%%screenElement% {}"

    @Test
    fun `on create screen files`() {
        whenever(codeDirectoryMock.findSubdirectory("com")).thenReturn(codeDirectoryMock)
        whenever(codeDirectoryMock.findSubdirectory("test")).thenReturn(null)
        whenever(codeDirectoryMock.createSubdirectory("test")).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findCodeSourceRoot()).thenReturn(codeSourceRootMock)
        whenever(codeSourceRootMock.directory).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findResourcesSourceRoot()).thenReturn(resourcesSourceRootMock)
        whenever(resourcesSourceRootMock.directory).thenReturn(resourcesDirectoryMock)
        whenever(resourcesDirectoryMock.findSubdirectory("layout")).thenReturn(null)
        whenever(resourcesDirectoryMock.createSubdirectory("layout")).thenReturn(resourcesDirectoryMock)

        val screenElements = listOf(ScreenElement("Presenter", testTemplate, FileType.KOTLIN), ScreenElement("View", testTemplate, FileType.LAYOUT_XML))
        whenever(settingsRepositoryMock.loadSettings()).thenReturn(Settings(screenElements, "", ""))

        fileCreator.createScreenFiles("com.test", "Test")

        verify(codeDirectoryMock).addFile(File("TestPresenter", "data class TestPresenter {}", FileType.KOTLIN))
        verify(resourcesDirectoryMock).addFile(File("test", "data class TestView {}", FileType.LAYOUT_XML))
    }
}