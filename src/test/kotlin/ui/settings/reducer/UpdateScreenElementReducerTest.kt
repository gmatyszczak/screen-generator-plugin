package ui.settings.reducer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState
import ui.settings.renderSampleCode
import ui.settings.renderSampleFileName

@ExperimentalCoroutinesApi
class UpdateScreenElementReducerTest {

    val state = MutableStateFlow(SettingsState())
    lateinit var reducer: UpdateScreenElementReducer

    @BeforeEach
    fun setup() {
        reducer = UpdateScreenElementReducer(state)
    }

    @Test
    fun `on invoke`() {
        val initialState = SettingsState(
            categories = listOf(
                CategoryScreenElements(
                    Category(),
                    listOf(ScreenElement(name = "test1"))
                )
            ),
            selectedElementIndex = 0,
            selectedCategoryIndex = 0
        )
        val updatedElement = ScreenElement(name = "test2")

        state.value = initialState
        reducer.invoke(UpdateScreenElement(updatedElement))

        state.value shouldBeEqualTo initialState.copy(
            categories = listOf(
                CategoryScreenElements(
                    Category(),
                    listOf(updatedElement)
                )
            ),
            fileNameRendered = updatedElement.renderSampleFileName(),
            sampleCode = updatedElement.renderSampleCode(),
            isModified = true
        )
    }
}
