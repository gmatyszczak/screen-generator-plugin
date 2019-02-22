package ui.settings

import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import data.repository.SettingsRepository
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
    private val testElement = ScreenElement("Test", testTemplate)
    private val unnamedElement = ScreenElement(UNNAMED_ELEMENT, TEMPLATE)

    @Test
    fun `on load view`() {
        val screenElements = listOf(testElement)
        whenever(settingsRepositoryMock.loadSettings()).thenReturn(Settings(screenElements, "", ""))

        presenter.onLoadView()

        verify(viewMock).setUpListeners()
        verify(viewMock).showScreenElements(screenElements)
        assertEquals(screenElements, presenter.screenElements)
        assertEquals(screenElements, presenter.initialScreenElements)
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
    fun `when index is in bounds on screen element select`() {
        val index = 0
        presenter.screenElements.add(testElement)

        presenter.onScreenElementSelect(index)

        inOrder(viewMock) {
            verify(viewMock).removeTextChangeListeners()
            verify(viewMock).showName("Test")
            verify(viewMock).showTemplate(testTemplate)
            verify(viewMock).showSampleCode(testElement.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME))
            verify(viewMock).addTextChangeListeners()
        }
        assertEquals(testElement, presenter.currentSelectedScreenElement)
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
        presenter.currentSelectedScreenElement = testElement
        presenter.screenElements.add(testElement)

        presenter.onNameChange("Test Test")

        assertEquals("Test Test", testElement.name)
        verify(viewMock).updateScreenElement(0, testElement)
        verify(viewMock).showSampleCode(testElement.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME))
        assertTrue(presenter.isModified)
    }

    @Test
    fun `on apply settings`() {
        presenter.screenElements.add(testElement)

        presenter.onApplySettings()

        verify(settingsRepositoryMock).update(Settings(listOf(testElement), "", ""))
        assertFalse(presenter.isModified)
        assertEquals(listOf(testElement), presenter.initialScreenElements)
    }

    @Test
    fun `on reset settings`() {
        presenter.initialScreenElements = listOf(testElement)

        presenter.screenElements.add(testElement)
        presenter.screenElements.add(testElement)

        presenter.onResetSettings()

        inOrder(viewMock) {
            verify(viewMock).clearScreenElements()
            verify(viewMock).showScreenElements(listOf(testElement))
        }
        assertEquals(listOf(testElement), presenter.screenElements)
        assertFalse(presenter.isModified)
    }

    @Test
    fun `on move down click`() {
        presenter.screenElements.addAll(listOf(testElement, unnamedElement))

        presenter.onMoveDownClick(0)

        assertEquals(listOf(unnamedElement, testElement), presenter.screenElements)
        assertTrue(presenter.isModified)
        verify(viewMock).updateScreenElement(0, unnamedElement)
        verify(viewMock).updateScreenElement(1, testElement)
        verify(viewMock).selectScreenElement(1)
    }

    @Test
    fun `on move up click`() {
        presenter.screenElements.addAll(listOf(testElement, unnamedElement))

        presenter.onMoveUpClick(1)

        assertEquals(listOf(unnamedElement, testElement), presenter.screenElements)
        assertTrue(presenter.isModified)
        verify(viewMock).updateScreenElement(0, unnamedElement)
        verify(viewMock).updateScreenElement(1, testElement)
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
}