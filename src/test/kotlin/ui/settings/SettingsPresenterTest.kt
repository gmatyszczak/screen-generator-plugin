package ui.settings

import com.nhaarman.mockitokotlin2.*
import data.repository.SettingsRepository
import model.FileType
import model.ScreenElement
import model.Settings
import model.Variable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class SettingsPresenterTest {

    @Mock
    private lateinit var viewMock: SettingsView

    @Mock
    private lateinit var settingsRepositoryMock: SettingsRepository

    @InjectMocks
    private lateinit var presenter: SettingsPresenter

    private val testTemplate = "data class ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value} {}"
    private val testElementKotlin = ScreenElement("Test", testTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
    private val testElementXml = ScreenElement("Test", FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)
    private val unnamedElement = ScreenElement.getDefault()
    private val activityBaseClass = "Activity"
    private val fragmentBaseClass = "Fragment"

    @Test
    fun `on load view`() {
        val screenElements = listOf(testElementKotlin)
        val settings = Settings(screenElements, activityBaseClass, fragmentBaseClass)
        whenever(settingsRepositoryMock.loadSettings()).thenReturn(settings)

        presenter.onLoadView()

        inOrder(viewMock) {
            verify(viewMock).setUpListeners()
            verify(viewMock).showScreenElements(screenElements)
            verify(viewMock).showActivityBaseClass(activityBaseClass)
            verify(viewMock).showFragmentBaseClass(fragmentBaseClass)
            verify(viewMock).addBaseClassTextChangeListeners()
        }
        assertEquals(screenElements, presenter.screenElements)
        assertEquals(settings, presenter.initialSettings)
        assertEquals(activityBaseClass, presenter.currentActivityBaseClass)
        assertEquals(fragmentBaseClass, presenter.currentFragmentBaseClass)
    }

    @Test
    fun `on add click`() {
        presenter.onAddClick()

        verify(viewMock).addScreenElement(unnamedElement)
        verify(viewMock).selectScreenElement(0)
        assertTrue(presenter.screenElements.contains(unnamedElement))
        assertTrue(presenter.isModified)
    }

    @Test
    fun `on delete click`() {
        presenter.screenElements.add(unnamedElement)

        presenter.onDeleteClick(0)

        verify(viewMock).removeScreenElement(0)
        assertTrue(presenter.screenElements.isEmpty())
        assertTrue(presenter.isModified)
    }

    @Test
    fun `when index is in bounds and file type kotlin on screen element select`() {
        val index = 0
        presenter.screenElements.add(testElementKotlin)
        presenter.currentActivityBaseClass = activityBaseClass

        presenter.onScreenElementSelect(index)

        inOrder(viewMock) {
            verify(viewMock).removeTextChangeListeners()
            verify(viewMock).showName("Test")
            verify(viewMock).showFileType(FileType.KOTLIN)
            verify(viewMock).showCodeTextFields(FileType.KOTLIN)
            verify(viewMock).swapToKotlinTemplateListener(false)
            verify(viewMock).showFileNameTemplate(testElementKotlin.fileNameTemplate)
            verify(viewMock).showFileNameSample("SampleTest")
            verify(viewMock).showTemplate(testTemplate)
            verify(viewMock).showSampleCode(testElementKotlin.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME, SAMPLE_ANDROID_COMPONENT, ""))
            verify(viewMock).addTextChangeListeners()
        }
        verifyNoMoreInteractions(viewMock)
        assertEquals(testElementKotlin, presenter.currentSelectedScreenElement)
    }

    @Test
    fun `when index is in bounds and file type xml on screen element select`() {
        val index = 0
        presenter.screenElements.add(testElementXml)
        presenter.currentActivityBaseClass = activityBaseClass

        presenter.onScreenElementSelect(index)

        inOrder(viewMock) {
            verify(viewMock).removeTextChangeListeners()
            verify(viewMock).showName("Test")
            verify(viewMock).showFileType(FileType.LAYOUT_XML)
            verify(viewMock).showCodeTextFields(FileType.LAYOUT_XML)
            verify(viewMock).swapToXmlTemplateListener(false)
            verify(viewMock).showFileNameTemplate(testElementXml.fileNameTemplate)
            verify(viewMock).showFileNameSample("activity_sample")
            verify(viewMock).showTemplate(FileType.LAYOUT_XML.defaultTemplate)
            verify(viewMock).showSampleCode(testElementXml.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME, SAMPLE_ANDROID_COMPONENT, ""))
            verify(viewMock).addTextChangeListeners()
        }
        verifyNoMoreInteractions(viewMock)
        assertEquals(testElementXml, presenter.currentSelectedScreenElement)
    }

    @Test
    fun `when index is not in bounds on screen element select`() {
        val index = 0

        presenter.onScreenElementSelect(index)

        inOrder(viewMock) {
            verify(viewMock).removeTextChangeListeners()
            verify(viewMock).showName("")
            verify(viewMock).showTemplate("")
            verify(viewMock).showSampleCode("")
            verify(viewMock).showFileNameTemplate("")
            verify(viewMock).showFileNameSample("")
        }
        assertEquals(null, presenter.currentSelectedScreenElement)
    }

    @Test
    fun `when current selected screen element is null on name change`() {
        presenter.onNameChange("Test")

        verifyZeroInteractions(viewMock)
        assertFalse(presenter.isModified)
    }

    @Test
    fun `when current selected screen element is not null on name change`() {
        presenter.currentSelectedScreenElement = testElementKotlin
        presenter.screenElements.add(testElementKotlin)
        presenter.currentActivityBaseClass = activityBaseClass

        presenter.onNameChange("Test Test")

        assertEquals("Test Test", testElementKotlin.name)
        verify(viewMock).updateScreenElement(0, testElementKotlin)
        verify(viewMock).showSampleCode(testElementKotlin.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME, SAMPLE_ANDROID_COMPONENT, ""))
        assertTrue(presenter.isModified)
    }

    @Test
    fun `on apply settings`() {
        presenter.screenElements.add(testElementKotlin)
        presenter.currentActivityBaseClass = activityBaseClass
        presenter.currentFragmentBaseClass = fragmentBaseClass

        presenter.onApplySettings()

        val settings = Settings(listOf(testElementKotlin), activityBaseClass, fragmentBaseClass)
        verify(settingsRepositoryMock).update(settings)
        assertFalse(presenter.isModified)
        assertEquals(settings, presenter.initialSettings)
    }

    @Test
    fun `on reset settings`() {
        val settings = Settings(listOf(testElementKotlin), activityBaseClass, fragmentBaseClass)
        presenter.initialSettings = settings

        presenter.screenElements.add(testElementKotlin)
        presenter.screenElements.add(testElementKotlin)

        presenter.onResetSettings()

        inOrder(viewMock) {
            verify(viewMock).clearScreenElements()
            verify(viewMock).showScreenElements(listOf(testElementKotlin))
            verify(viewMock).removeBaseClassTextChangeListeners()
            verify(viewMock).showActivityBaseClass(activityBaseClass)
            verify(viewMock).showFragmentBaseClass(fragmentBaseClass)
            verify(viewMock).addBaseClassTextChangeListeners()
        }
        assertEquals(listOf(testElementKotlin), presenter.screenElements)
        assertEquals(activityBaseClass, presenter.currentActivityBaseClass)
        assertEquals(fragmentBaseClass, presenter.currentFragmentBaseClass)
        assertFalse(presenter.isModified)
    }

    @Test
    fun `on move down click`() {
        presenter.screenElements.addAll(listOf(testElementKotlin, unnamedElement))

        presenter.onMoveDownClick(0)

        assertEquals(listOf(unnamedElement, testElementKotlin), presenter.screenElements)
        assertTrue(presenter.isModified)
        verify(viewMock).updateScreenElement(0, unnamedElement)
        verify(viewMock).updateScreenElement(1, testElementKotlin)
        verify(viewMock).selectScreenElement(1)
    }

    @Test
    fun `on move up click`() {
        presenter.screenElements.addAll(listOf(testElementKotlin, unnamedElement))

        presenter.onMoveUpClick(1)

        assertEquals(listOf(unnamedElement, testElementKotlin), presenter.screenElements)
        assertTrue(presenter.isModified)
        verify(viewMock).updateScreenElement(0, unnamedElement)
        verify(viewMock).updateScreenElement(1, testElementKotlin)
        verify(viewMock).selectScreenElement(0)
    }

    @Test
    fun `when current selected item is null on template change`() {
        presenter.onTemplateChange("")

        verifyZeroInteractions(viewMock)
        assertFalse(presenter.isModified)
    }

    @Test
    fun `when current selected item is not null on template change`() {
        presenter.currentSelectedScreenElement = unnamedElement
        presenter.currentActivityBaseClass = activityBaseClass

        presenter.onTemplateChange(testTemplate)

        verify(viewMock).showSampleCode(unnamedElement.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME, SAMPLE_ANDROID_COMPONENT, ""))
        assertTrue(presenter.isModified)
        assertEquals(testTemplate, presenter.currentSelectedScreenElement?.template)
    }

    @Test
    fun `on activity base class change`() {
        presenter.onActivityBaseClassChange(activityBaseClass)

        assertTrue(presenter.isModified)
        assertEquals(activityBaseClass, presenter.currentActivityBaseClass)
    }

    @Test
    fun `on fragment base class change`() {
        presenter.onFragmentBaseClassChange(fragmentBaseClass)

        assertTrue(presenter.isModified)
        assertEquals(fragmentBaseClass, presenter.currentFragmentBaseClass)
    }

    @Test
    fun `when file type kotlin on file type select`() {
        presenter.currentSelectedScreenElement = testElementXml
        presenter.currentActivityBaseClass = activityBaseClass

        presenter.onFileTypeSelect(FileType.KOTLIN)

        assertTrue(presenter.isModified)
        assertEquals(FileType.KOTLIN, presenter.currentSelectedScreenElement?.fileType)
        assertEquals(FileType.KOTLIN.defaultFileName, presenter.currentSelectedScreenElement?.fileNameTemplate)
        verify(viewMock).showCodeTextFields(FileType.KOTLIN)
        verify(viewMock).swapToKotlinTemplateListener(true)
        verify(viewMock).showFileNameTemplate(FileType.KOTLIN.defaultFileName)
        verify(viewMock).showFileNameSample("SampleTest")
        verify(viewMock).showTemplate(FileType.KOTLIN.defaultTemplate)
    }

    @Test
    fun `when file type xml on file type select`() {
        presenter.currentSelectedScreenElement = testElementKotlin
        presenter.currentActivityBaseClass = activityBaseClass

        presenter.onFileTypeSelect(FileType.LAYOUT_XML)

        assertTrue(presenter.isModified)
        assertEquals(FileType.LAYOUT_XML, presenter.currentSelectedScreenElement?.fileType)
        assertEquals(FileType.LAYOUT_XML.defaultFileName, presenter.currentSelectedScreenElement?.fileNameTemplate)
        verify(viewMock).showCodeTextFields(FileType.LAYOUT_XML)
        verify(viewMock).swapToXmlTemplateListener(true)
        verify(viewMock).showFileNameTemplate(FileType.LAYOUT_XML.defaultFileName)
        verify(viewMock).showFileNameSample("activity_sample")
        verify(viewMock).showTemplate(FileType.LAYOUT_XML.defaultTemplate)
    }

    @Test
    fun `on file name change`() {
        presenter.currentSelectedScreenElement = testElementKotlin
        presenter.currentActivityBaseClass = activityBaseClass

        presenter.onFileNameChange("${Variable.NAME.value}Test")

        assertEquals("${Variable.NAME.value}Test", testElementKotlin.fileNameTemplate)
        assertTrue(presenter.isModified)
        verify(viewMock).showFileNameSample("SampleTest")
    }

    @Test
    fun `on help click`() {
        presenter.onHelpClick()

        verify(viewMock).showHelp()
    }
}