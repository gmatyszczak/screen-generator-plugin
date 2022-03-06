package data.file

import data.repository.SettingsRepository
import data.repository.SourceRootRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import model.AndroidComponent
import model.Category
import model.CustomVariable
import model.FileType
import model.Module
import model.ScreenElement
import model.Variable
import org.junit.jupiter.api.Test

class FileCreatorImplTest {

    val module = Module("app", "app")
    val settingsRepositoryMock: SettingsRepository = mockk()
    val codeSourceRootMock: SourceRoot = mockk()
    val resourcesSourceRootMock: SourceRoot = mockk()
    val codeDirectoryMock: Directory = mockk(relaxUnitFun = true)
    val resourcesDirectoryMock: Directory = mockk(relaxUnitFun = true)
    val sourceRootRepositoryMock: SourceRootRepository = mockk()
    val fileCreator = FileCreatorImpl(settingsRepositoryMock, sourceRootRepositoryMock)

    val testKotlinTemplate =
        "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass ${Variable.NAME.value}%customVariable%${Variable.ANDROID_COMPONENT_NAME.value} : AppCompatActivity"
    val testXmlTemplate = "<FrameLayout></FrameLayout>"
    val screenElements = mutableListOf(
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

    val category = Category(id = 0)

    @Test
    fun `when android component is activity on create screen files`() {
        every { codeDirectoryMock.findSubdirectory("com") } returns codeDirectoryMock
        every { codeDirectoryMock.findSubdirectory("test") } returns null
        every { codeDirectoryMock.createSubdirectory("test") } returns codeDirectoryMock
        every { sourceRootRepositoryMock.findCodeSourceRoot(module, "test") } returns codeSourceRootMock
        every { codeSourceRootMock.directory } returns codeDirectoryMock
        every { sourceRootRepositoryMock.findCodeSourceRoot(module) } returns codeSourceRootMock
        every { sourceRootRepositoryMock.findResourcesSourceRoot(module) } returns resourcesSourceRootMock
        every { resourcesSourceRootMock.directory } returns resourcesDirectoryMock
        every { resourcesDirectoryMock.findSubdirectory("layout") } returns null
        every { resourcesDirectoryMock.createSubdirectory("layout") } returns resourcesDirectoryMock
        every { settingsRepositoryMock.loadScreenElements(0) } returns screenElements

        fileCreator.createScreenFiles(
            "com.test",
            "Test",
            AndroidComponent.ACTIVITY,
            module,
            category,
            mapOf(CustomVariable("customVariable") to "Custom")
        )

        verifySequence {
            codeDirectoryMock.findSubdirectory("com")
            codeDirectoryMock.findSubdirectory("test")
            codeDirectoryMock.createSubdirectory("test")
            codeDirectoryMock.addFile(
                File(
                    "TestActivity",
                    "package com.test\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass TestCustomActivity : AppCompatActivity",
                    FileType.KOTLIN
                )
            )
            codeDirectoryMock.findSubdirectory("com")
            codeDirectoryMock.findSubdirectory("test")
            codeDirectoryMock.createSubdirectory("test")

            codeDirectoryMock.addFile(
                File(
                    "TestPresenter",
                    "test",
                    FileType.KOTLIN
                )
            )
        }
        verifySequence {
            resourcesDirectoryMock.findSubdirectory("layout")
            resourcesDirectoryMock.createSubdirectory("layout")
            resourcesDirectoryMock.addFile(
                File(
                    "activity_test",
                    "<FrameLayout></FrameLayout>",
                    FileType.LAYOUT_XML
                )
            )
        }
    }

    @Test
    fun `when android component is fragment on create screen files`() {
        every { codeDirectoryMock.findSubdirectory("com") } returns codeDirectoryMock
        every { codeDirectoryMock.findSubdirectory("test") } returns null
        every { codeDirectoryMock.createSubdirectory("test") } returns codeDirectoryMock
        every { sourceRootRepositoryMock.findCodeSourceRoot(module) } returns codeSourceRootMock
        every { codeSourceRootMock.directory } returns codeDirectoryMock
        every { sourceRootRepositoryMock.findResourcesSourceRoot(module) } returns resourcesSourceRootMock
        every { resourcesSourceRootMock.directory } returns resourcesDirectoryMock
        every { resourcesDirectoryMock.findSubdirectory("layout") } returns null
        every { resourcesDirectoryMock.createSubdirectory("layout") } returns resourcesDirectoryMock
        every { settingsRepositoryMock.loadScreenElements(0) } returns screenElements
        every { codeDirectoryMock.findSubdirectory("abc") } returns null
        every { codeDirectoryMock.createSubdirectory("abc") } returns codeDirectoryMock
        every { codeDirectoryMock.findSubdirectory("def") } returns null
        every { codeDirectoryMock.createSubdirectory("def") } returns codeDirectoryMock

        fileCreator.createScreenFiles(
            "com.test",
            "Test",
            AndroidComponent.FRAGMENT,
            module,
            category,
            mapOf(CustomVariable("customVariable") to "Custom")
        )

        verifySequence {
            codeDirectoryMock.findSubdirectory("com")
            codeDirectoryMock.findSubdirectory("test")
            codeDirectoryMock.createSubdirectory("test")
            codeDirectoryMock.findSubdirectory("abc")
            codeDirectoryMock.createSubdirectory("abc")
            codeDirectoryMock.findSubdirectory("def")
            codeDirectoryMock.createSubdirectory("def")
            codeDirectoryMock.addFile(File("Custom", "test", FileType.KOTLIN))
            codeDirectoryMock.findSubdirectory("com")
            codeDirectoryMock.findSubdirectory("test")
            codeDirectoryMock.createSubdirectory("test")
            codeDirectoryMock.addFile(
                File(
                    "TestPresenter",
                    "test",
                    FileType.KOTLIN
                )
            )
        }
        verifySequence {
            resourcesDirectoryMock.findSubdirectory("layout")
            resourcesDirectoryMock.createSubdirectory("layout")
            resourcesDirectoryMock.addFile(
                File(
                    "fragment_test",
                    "<FrameLayout></FrameLayout>",
                    FileType.LAYOUT_XML
                )
            )
        }
    }

    @Test
    fun `when android component is none on create screen files`() {
        every { codeDirectoryMock.findSubdirectory("com") } returns codeDirectoryMock
        every { codeDirectoryMock.findSubdirectory("test") } returns null
        every { codeDirectoryMock.createSubdirectory("test") } returns codeDirectoryMock
        every { sourceRootRepositoryMock.findCodeSourceRoot(module) } returns codeSourceRootMock
        every { codeSourceRootMock.directory } returns codeDirectoryMock
        every { sourceRootRepositoryMock.findResourcesSourceRoot(module) } returns resourcesSourceRootMock
        every { resourcesSourceRootMock.directory } returns resourcesDirectoryMock
        every { resourcesDirectoryMock.findSubdirectory("layout") } returns null
        every { resourcesDirectoryMock.createSubdirectory("layout") } returns resourcesDirectoryMock
        every { settingsRepositoryMock.loadScreenElements(0) } returns screenElements

        fileCreator.createScreenFiles(
            "com.test",
            "Test",
            AndroidComponent.NONE,
            module,
            category,
            emptyMap()
        )

        verifySequence {
            codeDirectoryMock.findSubdirectory("com")
            codeDirectoryMock.findSubdirectory("test")
            codeDirectoryMock.createSubdirectory("test")
            codeDirectoryMock.addFile(
                File(
                    "TestPresenter",
                    "test",
                    FileType.KOTLIN
                )
            )
        }
        verifySequence {
            resourcesDirectoryMock.findSubdirectory("layout")
            resourcesDirectoryMock.createSubdirectory("layout")
            resourcesDirectoryMock.addFile(
                File(
                    "none_test",
                    "<FrameLayout></FrameLayout>",
                    FileType.LAYOUT_XML
                )
            )
        }
    }
}
