package data.file

import data.repository.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import model.AndroidComponent.ACTIVITY
import model.AndroidComponent.FRAGMENT
import model.AndroidComponent.NONE
import model.Category
import model.CustomVariable
import model.FileType.KOTLIN
import model.FileType.LAYOUT_XML
import model.Module
import model.ScreenElement
import model.ScreenElementType.FILE_MODIFICATION
import model.ScreenElementType.NEW_FILE
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ScreenElementCreatorTest {

    val packageName = "packageName"
    val screenName = "screenName"
    val customVariables: Map<CustomVariable, String> = mockk()
    val module: Module = mockk()
    val category = Category(id = 0)
    val directory: Directory = mockk(relaxUnitFun = true)
    val fileActivity: File = mockk()
    val fileFragment: File = mockk()
    val filePresenter: File = mockk()
    val fileLayout: File = mockk()
    val modification: Modification = mockk()
    val screenElementActivity: ScreenElement = mockk {
        every { fileType } returns KOTLIN
        every { relatedAndroidComponent } returns ACTIVITY
        every { type } returns NEW_FILE
    }
    val screenElementFragment: ScreenElement = mockk {
        every { fileType } returns KOTLIN
        every { relatedAndroidComponent } returns FRAGMENT
        every { type } returns NEW_FILE
    }
    val screenElementPresenter: ScreenElement = mockk {
        every { fileType } returns KOTLIN
        every { relatedAndroidComponent } returns NONE
        every { type } returns NEW_FILE
    }
    val screenElementLayout: ScreenElement = mockk {
        every { fileType } returns LAYOUT_XML
        every { relatedAndroidComponent } returns NONE
        every { type } returns NEW_FILE
    }
    val screenElementModification: ScreenElement = mockk {
        every { fileType } returns KOTLIN
        every { relatedAndroidComponent } returns ACTIVITY
        every { type } returns FILE_MODIFICATION
    }
    val screenElements = mutableListOf(
        screenElementActivity,
        screenElementFragment,
        screenElementPresenter,
        screenElementLayout,
        screenElementModification,
    )
    val settingsRepositoryMock: SettingsRepository = mockk()
    val targetDirectory: TargetDirectory = mockk()
    val screenElementCreator = ScreenElementCreator(settingsRepositoryMock, targetDirectory)

    @BeforeEach
    fun setup() {
        mockkStatic(ScreenElement::toFile, ScreenElement::toModification)
        every { settingsRepositoryMock.loadScreenElements(0) } returns screenElements
        every { targetDirectory.findOrCreate(any(), module, packageName) } returns directory
        every { screenElementActivity.toFile(screenName, packageName, any(), customVariables) } returns fileActivity
        every { screenElementFragment.toFile(screenName, packageName, any(), customVariables) } returns fileFragment
        every { screenElementPresenter.toFile(screenName, packageName, any(), customVariables) } returns filePresenter
        every { screenElementLayout.toFile(screenName, packageName, any(), customVariables) } returns fileLayout
        every {
            screenElementModification.toModification(
                screenName,
                packageName,
                any(),
                customVariables
            )
        } returns modification
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when android component is activity on create`() {
        screenElementCreator.create(
            packageName,
            screenName,
            ACTIVITY,
            module,
            category,
            customVariables
        )

        verify {
            directory.addFile(fileActivity)
            directory.addFile(filePresenter)
            directory.addFile(fileLayout)
            directory.modifyFile(modification)
        }
    }

    @Test
    fun `when android component is fragment on create`() {
        screenElementCreator.create(
            packageName,
            screenName,
            FRAGMENT,
            module,
            category,
            customVariables
        )

        verify {
            directory.addFile(fileFragment)
            directory.addFile(filePresenter)
            directory.addFile(fileLayout)
        }
    }

    @Test
    fun `when android component is none on create`() {
        screenElementCreator.create(
            packageName,
            screenName,
            NONE,
            module,
            category,
            customVariables
        )

        verify {
            directory.addFile(filePresenter)
            directory.addFile(fileLayout)
        }
    }
}
