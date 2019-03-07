package data.file

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import data.repository.SettingsRepository
import data.repository.SourceRootRepository
import model.*
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

    private val moduleName = "app"

    @InjectMocks
    private lateinit var fileCreator: FileCreatorImpl

    private val testKotlinTemplate = "package ${Variable.PACKAGE_NAME.value}\n\nimport ${Variable.ANDROID_COMPONENT_FULL_CLASS_NAME.value}\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : ${Variable.ANDROID_COMPONENT_CLASS_NAME.value}"
    private val testXmlTemplate = "<FrameLayout></FrameLayout>"
    private val screenElements = mutableListOf(
            ScreenElement("Presenter", testKotlinTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
            ScreenElement("View", testXmlTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName))

    @Test
    fun `when android component is activity on create screen files`() {
        whenever(codeDirectoryMock.findSubdirectory("com")).thenReturn(codeDirectoryMock)
        whenever(codeDirectoryMock.findSubdirectory("test")).thenReturn(null)
        whenever(codeDirectoryMock.createSubdirectory("test")).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findCodeSourceRoot(moduleName)).thenReturn(codeSourceRootMock)
        whenever(codeSourceRootMock.directory).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findResourcesSourceRoot(moduleName)).thenReturn(resourcesSourceRootMock)
        whenever(resourcesSourceRootMock.directory).thenReturn(resourcesDirectoryMock)
        whenever(resourcesDirectoryMock.findSubdirectory("layout")).thenReturn(null)
        whenever(resourcesDirectoryMock.createSubdirectory("layout")).thenReturn(resourcesDirectoryMock)
        whenever(settingsRepositoryMock.loadSettings()).thenReturn(Settings(screenElements, "com.AppCompatActivity", "com.Fragment"))

        fileCreator.createScreenFiles("com.test", "Test", AndroidComponent.ACTIVITY, moduleName)

        verify(codeDirectoryMock).addFile(File("TestPresenter", "package com.test\n\nimport com.AppCompatActivity\n\nclass TestActivity : AppCompatActivity", FileType.KOTLIN))
        verify(resourcesDirectoryMock).addFile(File("activity_test", "<FrameLayout></FrameLayout>", FileType.LAYOUT_XML))
    }

    @Test
    fun `when android component is fragment on create screen files`() {
        whenever(codeDirectoryMock.findSubdirectory("com")).thenReturn(codeDirectoryMock)
        whenever(codeDirectoryMock.findSubdirectory("test")).thenReturn(null)
        whenever(codeDirectoryMock.createSubdirectory("test")).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findCodeSourceRoot(moduleName)).thenReturn(codeSourceRootMock)
        whenever(codeSourceRootMock.directory).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findResourcesSourceRoot(moduleName)).thenReturn(resourcesSourceRootMock)
        whenever(resourcesSourceRootMock.directory).thenReturn(resourcesDirectoryMock)
        whenever(resourcesDirectoryMock.findSubdirectory("layout")).thenReturn(null)
        whenever(resourcesDirectoryMock.createSubdirectory("layout")).thenReturn(resourcesDirectoryMock)
        whenever(settingsRepositoryMock.loadSettings()).thenReturn(Settings(screenElements, "com.AppCompatActivity", "com.Fragment"))

        fileCreator.createScreenFiles("com.test", "Test", AndroidComponent.FRAGMENT, moduleName)

        verify(codeDirectoryMock).addFile(File("TestPresenter", "package com.test\n\nimport com.Fragment\n\nclass TestFragment : Fragment", FileType.KOTLIN))
        verify(resourcesDirectoryMock).addFile(File("fragment_test", "<FrameLayout></FrameLayout>", FileType.LAYOUT_XML))
    }
}