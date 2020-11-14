package ui.settings.reducer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import ui.settings.SettingsState
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class SelectScreenElementReducerImplTest : BaseReducerTest() {

    private lateinit var reducer: SelectScreenElementReducerImpl

    @Before
    fun setup() {
        reducer = SelectScreenElementReducerImpl(state, effectMock, TestCoroutineScope())
    }

    @Test
    fun `when screen elements empty on invoke`() {
        reducer.invoke(0)

        assertEquals(
            SettingsState(
                selectedElementIndex = null,
                fileNameRendered = "",
                sampleCode = ""
            ),
            state.value
        )
    }

    @Test
    fun `when index not in bounds on invoke`() {
        val initialState = SettingsState(screenElements = listOf(ScreenElement()))
        state.value = initialState

        reducer.invoke(10)

        assertEquals(
            initialState.copy(
                selectedElementIndex = null,
                fileNameRendered = "",
                sampleCode = ""
            ),
            state.value
        )
    }

    @Test
    fun `when index in bounds on invoke`() {
        val screenElement = ScreenElement(
            name = "test",
            template = "test",
            fileNameTemplate = "test"
        )
        val initialState = SettingsState(
            screenElements = listOf(screenElement),
            activityBaseClass = "test"
        )
        state.value = initialState

        reducer.invoke(0)

        assertEquals(
            initialState.copy(
                selectedElementIndex = 0,
                fileNameRendered = "test.kt",
                sampleCode = "test"
            ),
            state.value
        )
    }
}