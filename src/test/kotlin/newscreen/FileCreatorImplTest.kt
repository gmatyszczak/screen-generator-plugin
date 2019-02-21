package newscreen

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import model.ScreenElement
import newscreen.files.Directory
import newscreen.files.File
import newscreen.files.SourceRoot
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import settings.SettingsRepository

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
        whenever(settingsRepositoryMock.loadScreenElements()).thenReturn(listOf(ScreenElement("Presenter", testTemplate), ScreenElement("View", testTemplate)))

        fileCreator.createScreenFiles(sourceRootMock, "com.test", "Test")

        verify(directoryMock).addFile(File("TestPresenter", "data class TestPresenter {}"))
        verify(directoryMock).addFile(File("TestView", "data class TestView {}"))
    }
}