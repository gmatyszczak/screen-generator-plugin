package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import kotlinx.coroutines.test.TestCoroutineScope
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState

class ChangeFileNameReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var updateScreenElementReducerMock: UpdateScreenElementReducer

    private lateinit var reducer: ChangeFileNameReducerImpl

    @Before
    fun setup() {
        reducer = ChangeFileNameReducerImpl(state, effectMock, TestCoroutineScope(), updateScreenElementReducerMock)
    }

    @Test
    fun `if selected element not null on invoke`() {
        state.value = SettingsState(
            screenElements = listOf(ScreenElement()),
            selectedElementIndex = 0
        )

        reducer.invoke("test")

        verify(updateScreenElementReducerMock).invoke(ScreenElement(fileNameTemplate = "test"))
    }

    @Test
    fun `if selected element null on invoke`() {
        reducer.invoke("test")

        verifyZeroInteractions(updateScreenElementReducerMock)
    }
}