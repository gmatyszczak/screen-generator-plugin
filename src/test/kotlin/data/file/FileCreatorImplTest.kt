package data.file

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import data.repository.SettingsRepository
import data.repository.SourceRootRepository
import model.AndroidComponent
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

    private val testKotlinTemplate = "package %packageName%\n\nimport %androidComponentFullName%\n\nclass %name%%androidComponentShortName% : %androidComponentLongName%"
    private val testXmlTemplate = "<FrameLayout></FrameLayout>"

    @Test
    fun `when android component is activity on create screen files`() {
        whenever(codeDirectoryMock.findSubdirectory("com")).thenReturn(codeDirectoryMock)
        whenever(codeDirectoryMock.findSubdirectory("test")).thenReturn(null)
        whenever(codeDirectoryMock.createSubdirectory("test")).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findCodeSourceRoot()).thenReturn(codeSourceRootMock)
        whenever(codeSourceRootMock.directory).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findResourcesSourceRoot()).thenReturn(resourcesSourceRootMock)
        whenever(resourcesSourceRootMock.directory).thenReturn(resourcesDirectoryMock)
        whenever(resourcesDirectoryMock.findSubdirectory("layout")).thenReturn(null)
        whenever(resourcesDirectoryMock.createSubdirectory("layout")).thenReturn(resourcesDirectoryMock)
        val screenElements = listOf(ScreenElement("Presenter", testKotlinTemplate, FileType.KOTLIN), ScreenElement("View", testXmlTemplate, FileType.LAYOUT_XML))
        whenever(settingsRepositoryMock.loadSettings()).thenReturn(Settings(screenElements, "com.AppCompatActivity", "com.Fragment"))

        fileCreator.createScreenFiles("com.test", "Test", AndroidComponent.ACTIVITY)

        verify(codeDirectoryMock).addFile(File("TestPresenter", "package com.test\n\nimport com.AppCompatActivity\n\nclass TestActivity : AppCompatActivity", FileType.KOTLIN))
        verify(resourcesDirectoryMock).addFile(File("activity_test", "<FrameLayout></FrameLayout>", FileType.LAYOUT_XML))
    }

    @Test
    fun `when android component is fragment on create screen files`() {
        whenever(codeDirectoryMock.findSubdirectory("com")).thenReturn(codeDirectoryMock)
        whenever(codeDirectoryMock.findSubdirectory("test")).thenReturn(null)
        whenever(codeDirectoryMock.createSubdirectory("test")).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findCodeSourceRoot()).thenReturn(codeSourceRootMock)
        whenever(codeSourceRootMock.directory).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findResourcesSourceRoot()).thenReturn(resourcesSourceRootMock)
        whenever(resourcesSourceRootMock.directory).thenReturn(resourcesDirectoryMock)
        whenever(resourcesDirectoryMock.findSubdirectory("layout")).thenReturn(null)
        whenever(resourcesDirectoryMock.createSubdirectory("layout")).thenReturn(resourcesDirectoryMock)
        val screenElements = listOf(ScreenElement("Presenter", testKotlinTemplate, FileType.KOTLIN), ScreenElement("View", testXmlTemplate, FileType.LAYOUT_XML))
        whenever(settingsRepositoryMock.loadSettings()).thenReturn(Settings(screenElements, "com.AppCompatActivity", "com.Fragment"))

        fileCreator.createScreenFiles("com.test", "Test", AndroidComponent.FRAGMENT)

        verify(codeDirectoryMock).addFile(File("TestPresenter", "package com.test\n\nimport com.Fragment\n\nclass TestFragment : Fragment", FileType.KOTLIN))
        verify(resourcesDirectoryMock).addFile(File("fragment_test", "<FrameLayout></FrameLayout>", FileType.LAYOUT_XML))
    }
}