package data.file

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import data.repository.SettingsRepository
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
    private lateinit var sourceRootMock: SourceRoot

    @Mock
    private lateinit var directoryMock: Directory

    @InjectMocks
    private lateinit var fileCreator: FileCreatorImpl

    private val testTemplate = "data class %name%%screenElement% {}"

    @Test
    fun `on create screen files`() {
        whenever(directoryMock.findSubdirectory("com")).thenReturn(directoryMock)
        whenever(directoryMock.findSubdirectory("test")).thenReturn(null)
        whenever(directoryMock.createSubdirectory("test")).thenReturn(directoryMock)
        whenever(sourceRootMock.directory).thenReturn(directoryMock)
        val screenElements = listOf(ScreenElement("Presenter", testTemplate, FileType.KOTLIN), ScreenElement("View", testTemplate, FileType.KOTLIN))
        whenever(settingsRepositoryMock.loadSettings()).thenReturn(Settings(screenElements, "", ""))

        fileCreator.createScreenFiles(sourceRootMock, "com.test", "Test")

        verify(directoryMock).addFile(File("TestPresenter", "data class TestPresenter {}"))
        verify(directoryMock).addFile(File("TestView", "data class TestView {}"))
    }
}