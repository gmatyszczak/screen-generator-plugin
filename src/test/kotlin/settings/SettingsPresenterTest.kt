package settings

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class SettingsPresenterTest {

    @Mock
    private lateinit var viewMock: SettingsView

    private lateinit var presenter: SettingsPresenter

    @Before
    fun setUp() {
        presenter = SettingsPresenter(viewMock)
    }

    @Test
    fun `on load view`() {
        presenter.onLoadView()

        verify(viewMock).setUpListeners()
    }

    @Test
    fun `on add click`() {
        val element = ScreenElement(UNNAMED_ELEMENT)

        presenter.onAddClick()

        verify(viewMock).addScreenElement(element)
        verify(viewMock).selectLastScreenElement()
        assertTrue(presenter.screenElements.contains(element))
    }

    @Test
    fun `on delete click`() {
        val element = ScreenElement(UNNAMED_ELEMENT)
        presenter.screenElements.add(element)

        presenter.onDeleteClick(0)

        verify(viewMock).removeScreenElement(0)
        assertTrue(presenter.screenElements.isEmpty())
    }

    @Test
    fun `when index is in bounds on screen element select`() {
        val index = 0
        val element = ScreenElement("Test")
        presenter.screenElements.add(element)

        presenter.onScreenElementSelect(index)

        verify(viewMock).removeCurrentNameChangeListener()
        verify(viewMock).showName("Test")
        verify(viewMock).addNameChangeListener()
        assertEquals(element, presenter.currentSelectedScreenElement)
    }

    @Test
    fun `when index is not in bounds on screen element select`() {
        val index = 0

        presenter.onScreenElementSelect(index)

        verify(viewMock).removeCurrentNameChangeListener()
        verify(viewMock).showName("")
        assertEquals(null, presenter.currentSelectedScreenElement)
    }

    @Test
    fun `when current selected screen element is null on name change`() {
        presenter.onNameChange("Test")

        verifyZeroInteractions(viewMock)
    }

    @Test
    fun `when current selected screen element is not null on name change`() {
        val screenElement = ScreenElement("Test")
        presenter.currentSelectedScreenElement = screenElement
        presenter.screenElements.add(screenElement)

        presenter.onNameChange("Test Test")

        assertEquals("Test Test", screenElement.name)
        verify(viewMock).updateScreenElement(0, screenElement)
    }

}