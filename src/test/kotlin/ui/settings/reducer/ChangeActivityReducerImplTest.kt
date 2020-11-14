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
class ChangeActivityReducerImplTest : BaseReducerTest() {


    private lateinit var reducer: ChangeActivityReducerImpl

    private val screenElement = ScreenElement(name = "test", template = "test", fileNameTemplate = "test")
    private val initialState = SettingsState(
        screenElements = listOf(screenElement),
        selectedElementIndex = 0
    )

    @Before
    fun setup() {
        state.value = initialState
        reducer = ChangeActivityReducerImpl(state, effectMock, TestCoroutineScope())
    }

    @Test
    fun `on invoke`() {
        reducer.invoke("test")

        assertEquals(
            initialState.copy(
                sampleCode = screenElement.renderSampleCode("test"),
                fileNameRendered = screenElement.renderSampleFileName("test"),
                activityBaseClass = "test",
                isModified = true
            ),
            state.value
        )


    }
}