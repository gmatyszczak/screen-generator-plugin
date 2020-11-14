package ui.settings.reducer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ui.settings.SettingsState
import ui.settings.renderSampleCode
import ui.settings.renderSampleFileName

@ExperimentalCoroutinesApi
class ChangeFragmentReducerImplTest : BaseReducerTest() {


    private lateinit var reducer: ChangeFragmentReducerImpl

    private val screenElement = ScreenElement(name = "test", template = "test", fileNameTemplate = "test")
    private val initialState = SettingsState(
        screenElements = listOf(screenElement),
        selectedElementIndex = 0
    )

    @Before
    fun setup() {
        state.value = initialState
        reducer = ChangeFragmentReducerImpl(state, effectMock, TestCoroutineScope())
    }

    @Test
    fun `on invoke`() {
        reducer.invoke("test")

        assertEquals(
            initialState.copy(
                sampleCode = screenElement.renderSampleCode(""),
                fileNameRendered = screenElement.renderSampleFileName(""),
                fragmentBaseClass = "test",
                isModified = true
            ),
            state.value
        )


    }
}