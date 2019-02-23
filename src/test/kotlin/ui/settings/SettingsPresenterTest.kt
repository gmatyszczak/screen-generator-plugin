package ui.settings

import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
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

    private val testTemplate = "data class %name%%screenElement% {}"
    private val testElementKotlin = ScreenElement("Test", testTemplate, FileType.KOTLIN)
    private val testElementXml = ScreenElement("Test", FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML)
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

        presenter.onScreenElementSelect(index)

        inOrder(viewMock) {
            verify(viewMock).removeTextChangeListeners()
            verify(viewMock).showName("Test")
            verify(viewMock).showFileType(FileType.KOTLIN)
            verify(viewMock).hideXmlTextFields()
            verify(viewMock).showKotlinTextFields()
            verify(viewMock).swapToKotlinTemplateListener()
            verify(viewMock).showTemplate(testTemplate)
            verify(viewMock).showSampleCode(testElementKotlin.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME))
            verify(viewMock).addTextChangeListeners()
        }
        assertEquals(testElementKotlin, presenter.currentSelectedScreenElement)
    }

    @Test
    fun `when index is in bounds and file type xml on screen element select`() {
        val index = 0
        presenter.screenElements.add(testElementXml)

        presenter.onScreenElementSelect(index)

        inOrder(viewMock) {
            verify(viewMock).removeTextChangeListeners()
            verify(viewMock).showName("Test")
            verify(viewMock).showFileType(FileType.LAYOUT_XML)
            verify(viewMock).hideKotlinTextFields()
            verify(viewMock).showXmlTextFields()
            verify(viewMock).swapToXmlTemplateListener()
            verify(viewMock).showTemplate(FileType.LAYOUT_XML.defaultTemplate)
            verify(viewMock).showSampleCode(testElementXml.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME))
            verify(viewMock).addTextChangeListeners()
        }
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

        presenter.onNameChange("Test Test")

        assertEquals("Test Test", testElementKotlin.name)
        verify(viewMock).updateScreenElement(0, testElementKotlin)
        verify(viewMock).showSampleCode(testElementKotlin.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME))
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

        presenter.onTemplateChange(testTemplate)

        verify(viewMock).showSampleCode(unnamedElement.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME))
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

        presenter.onFileTypeSelect(FileType.KOTLIN)

        assertTrue(presenter.isModified)
        assertEquals(FileType.KOTLIN, presenter.currentSelectedScreenElement?.fileType)
        verify(viewMock).hideXmlTextFields()
        verify(viewMock).showKotlinTextFields()
        verify(viewMock).swapToKotlinTemplateListener()
        verify(viewMock).showTemplate(FileType.KOTLIN.defaultTemplate)
    }

    @Test
    fun `when file type xml on file type select`() {
        presenter.currentSelectedScreenElement = testElementKotlin

        presenter.onFileTypeSelect(FileType.LAYOUT_XML)

        assertTrue(presenter.isModified)
        assertEquals(FileType.LAYOUT_XML, presenter.currentSelectedScreenElement?.fileType)
        verify(viewMock).hideKotlinTextFields()
        verify(viewMock).showXmlTextFields()
        verify(viewMock).swapToXmlTemplateListener()
        verify(viewMock).showTemplate(FileType.LAYOUT_XML.defaultTemplate)
    }
}