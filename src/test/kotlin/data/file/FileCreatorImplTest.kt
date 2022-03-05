package data.file

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import data.repository.SettingsRepository
import data.repository.SourceRootRepository
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

    private val module = Module("app", "app")

    @InjectMocks
    private lateinit var fileCreator: FileCreatorImpl

    private val testKotlinTemplate =
        "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass ${Variable.NAME.value}%customVariable%${Variable.ANDROID_COMPONENT_NAME.value} : AppCompatActivity"
    private val testXmlTemplate = "<FrameLayout></FrameLayout>"
    private val screenElements = mutableListOf(
        ScreenElement(
            "Activity",
            testKotlinTemplate,
            FileType.KOTLIN,
            FileType.KOTLIN.defaultFileName,
            AndroidComponent.ACTIVITY,
            sourceSet = "test"
        ),
        ScreenElement(
            "Fragment",
            "test",
            FileType.KOTLIN,
            "%customVariable%",
            AndroidComponent.FRAGMENT,
            subdirectory = "abc/def"
        ),
        ScreenElement("Presenter", "test", FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
        ScreenElement("View", testXmlTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)
    )

    private val category = Category(id = 0)

    @Test
    fun `when android component is activity on create screen files`() {
        whenever(codeDirectoryMock.findSubdirectory("com")).thenReturn(codeDirectoryMock)
        whenever(codeDirectoryMock.findSubdirectory("test")).thenReturn(null)
        whenever(codeDirectoryMock.createSubdirectory("test")).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findCodeSourceRoot(module, "test")).thenReturn(codeSourceRootMock)
        whenever(codeSourceRootMock.directory).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findCodeSourceRoot(module)).thenReturn(codeSourceRootMock)
        whenever(sourceRootRepositoryMock.findResourcesSourceRoot(module)).thenReturn(resourcesSourceRootMock)
        whenever(resourcesSourceRootMock.directory).thenReturn(resourcesDirectoryMock)
        whenever(resourcesDirectoryMock.findSubdirectory("layout")).thenReturn(null)
        whenever(resourcesDirectoryMock.createSubdirectory("layout")).thenReturn(resourcesDirectoryMock)
        whenever(settingsRepositoryMock.loadScreenElements(0)).thenReturn(screenElements)

        fileCreator.createScreenFiles(
            "com.test",
            "Test",
            AndroidComponent.ACTIVITY,
            module,
            category,
            mapOf(CustomVariable("customVariable") to "Custom")
        )

        inOrder(codeDirectoryMock) {
            verify(codeDirectoryMock).findSubdirectory("com")
            verify(codeDirectoryMock).findSubdirectory("test")
            verify(codeDirectoryMock).createSubdirectory("test")
            verify(codeDirectoryMock).addFile(
                File(
                    "TestActivity",
                    "package com.test\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass TestCustomActivity : AppCompatActivity",
                    FileType.KOTLIN
                )
            )
            verify(codeDirectoryMock).findSubdirectory("com")
            verify(codeDirectoryMock).findSubdirectory("test")
            verify(codeDirectoryMock).createSubdirectory("test")
            verify(codeDirectoryMock).addFile(
                File(
                    "TestPresenter",
                    "test",
                    FileType.KOTLIN
                )
            )
            verifyNoMoreInteractions()
        }
        inOrder(resourcesDirectoryMock) {
            verify(resourcesDirectoryMock).findSubdirectory("layout")
            verify(resourcesDirectoryMock).createSubdirectory("layout")
            verify(resourcesDirectoryMock).addFile(
                File(
                    "activity_test",
                    "<FrameLayout></FrameLayout>",
                    FileType.LAYOUT_XML
                )
            )
            verifyNoMoreInteractions()
        }
        verifyNoMoreInteractions(codeDirectoryMock, resourcesDirectoryMock)
    }

    @Test
    fun `when android component is fragment on create screen files`() {
        whenever(codeDirectoryMock.findSubdirectory("com")).thenReturn(codeDirectoryMock)
        whenever(codeDirectoryMock.findSubdirectory("test")).thenReturn(null)
        whenever(codeDirectoryMock.createSubdirectory("test")).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findCodeSourceRoot(module)).thenReturn(codeSourceRootMock)
        whenever(codeSourceRootMock.directory).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findResourcesSourceRoot(module)).thenReturn(resourcesSourceRootMock)
        whenever(resourcesSourceRootMock.directory).thenReturn(resourcesDirectoryMock)
        whenever(resourcesDirectoryMock.findSubdirectory("layout")).thenReturn(null)
        whenever(resourcesDirectoryMock.createSubdirectory("layout")).thenReturn(resourcesDirectoryMock)
        whenever(settingsRepositoryMock.loadScreenElements(0)).thenReturn(screenElements)
        whenever(codeDirectoryMock.findSubdirectory("abc")) doReturn null
        whenever(codeDirectoryMock.createSubdirectory("abc")) doReturn codeDirectoryMock
        whenever(codeDirectoryMock.findSubdirectory("def")) doReturn null
        whenever(codeDirectoryMock.createSubdirectory("def")) doReturn codeDirectoryMock

        fileCreator.createScreenFiles(
            "com.test",
            "Test",
            AndroidComponent.FRAGMENT,
            module,
            category,
            mapOf(CustomVariable("customVariable") to "Custom")
        )

        inOrder(codeDirectoryMock) {
            verify(codeDirectoryMock).findSubdirectory("com")
            verify(codeDirectoryMock).findSubdirectory("test")
            verify(codeDirectoryMock).createSubdirectory("test")
            verify(codeDirectoryMock).findSubdirectory("abc")
            verify(codeDirectoryMock).createSubdirectory("abc")
            verify(codeDirectoryMock).findSubdirectory("def")
            verify(codeDirectoryMock).createSubdirectory("def")
            verify(codeDirectoryMock).addFile(File("Custom", "test", FileType.KOTLIN))
            verify(codeDirectoryMock).findSubdirectory("com")
            verify(codeDirectoryMock).findSubdirectory("test")
            verify(codeDirectoryMock).createSubdirectory("test")
            verify(codeDirectoryMock).addFile(
                File(
                    "TestPresenter",
                    "test",
                    FileType.KOTLIN
                )
            )
            verifyNoMoreInteractions()
        }
        inOrder(resourcesDirectoryMock) {
            verify(resourcesDirectoryMock).findSubdirectory("layout")
            verify(resourcesDirectoryMock).createSubdirectory("layout")
            verify(resourcesDirectoryMock).addFile(
                File(
                    "fragment_test",
                    "<FrameLayout></FrameLayout>",
                    FileType.LAYOUT_XML
                )
            )
            verifyNoMoreInteractions()
        }
        verifyNoMoreInteractions(codeDirectoryMock)
        verifyNoMoreInteractions(resourcesDirectoryMock)
    }

    @Test
    fun `when android component is none on create screen files`() {
        whenever(codeDirectoryMock.findSubdirectory("com")).thenReturn(codeDirectoryMock)
        whenever(codeDirectoryMock.findSubdirectory("test")).thenReturn(null)
        whenever(codeDirectoryMock.createSubdirectory("test")).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findCodeSourceRoot(module)).thenReturn(codeSourceRootMock)
        whenever(codeSourceRootMock.directory).thenReturn(codeDirectoryMock)
        whenever(sourceRootRepositoryMock.findResourcesSourceRoot(module)).thenReturn(resourcesSourceRootMock)
        whenever(resourcesSourceRootMock.directory).thenReturn(resourcesDirectoryMock)
        whenever(resourcesDirectoryMock.findSubdirectory("layout")).thenReturn(null)
        whenever(resourcesDirectoryMock.createSubdirectory("layout")).thenReturn(resourcesDirectoryMock)
        whenever(settingsRepositoryMock.loadScreenElements(0)).thenReturn(screenElements)

        fileCreator.createScreenFiles("com.test", "Test", AndroidComponent.NONE, module, category, emptyMap())

        inOrder(codeDirectoryMock) {
            verify(codeDirectoryMock).findSubdirectory("com")
            verify(codeDirectoryMock).findSubdirectory("test")
            verify(codeDirectoryMock).createSubdirectory("test")
            verify(codeDirectoryMock).addFile(
                File(
                    "TestPresenter",
                    "test",
                    FileType.KOTLIN
                )
            )
            verifyNoMoreInteractions()
        }
        inOrder(resourcesDirectoryMock) {
            verify(resourcesDirectoryMock).findSubdirectory("layout")
            verify(resourcesDirectoryMock).createSubdirectory("layout")
            verify(resourcesDirectoryMock).addFile(
                File(
                    "none_test",
                    "<FrameLayout></FrameLayout>",
                    FileType.LAYOUT_XML
                )
            )
            verifyNoMoreInteractions()
        }
        verifyNoMoreInteractions(codeDirectoryMock)
        verifyNoMoreInteractions(resourcesDirectoryMock)
    }
}
