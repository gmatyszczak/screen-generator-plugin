package ui.settings.reducer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import ui.settings.SettingsState
import ui.settings.renderSampleCode
import ui.settings.renderSampleFileName
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class UpdateScreenElementReducerImplTest : BaseReducerTest() {

    private lateinit var reducer: UpdateScreenElementReducerImpl

    @Before
    fun setup() {
        reducer = UpdateScreenElementReducerImpl(state, effectMock, TestCoroutineScope())
    }

    @Test
    fun `on invoke`() {
        val initialState = SettingsState(
            screenElements = listOf(
                ScreenElement(name = "test1")
            ),
            selectedElementIndex = 0
        )
        val updatedElement = ScreenElement(name = "test2")

        state.value = initialState
        reducer.invoke(updatedElement)

        assertEquals(
            initialState.copy(
                screenElements = listOf(updatedElement),
                fileNameRendered = updatedElement.renderSampleFileName(),
                sampleCode = updatedElement.renderSampleCode(),
                isModified = true
            ),
            state.value
        )
    }
}