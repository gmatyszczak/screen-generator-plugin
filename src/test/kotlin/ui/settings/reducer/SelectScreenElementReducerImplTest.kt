package ui.settings.reducer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class SelectScreenElementReducerImplTest : BaseReducerTest() {

    lateinit var reducer: SelectScreenElementReducerImpl

    @BeforeEach
    fun setup() {
        reducer = SelectScreenElementReducerImpl(state, effectMock, TestCoroutineScope())
    }

    @Test
    fun `when screen elements empty on invoke`() {
        reducer.invoke(0)

        state.value shouldBeEqualTo SettingsState(
            selectedElementIndex = null,
            fileNameRendered = "",
            sampleCode = ""
        )
    }

    @Test
    fun `when index not in bounds on invoke`() {
        val initialState = SettingsState(
            categories = listOf(CategoryScreenElements(Category(), listOf(ScreenElement()))),
            selectedCategoryIndex = 0
        )
        state.value = initialState

        reducer.invoke(10)

        state.value shouldBeEqualTo initialState.copy(
            selectedElementIndex = null,
            fileNameRendered = "",
            sampleCode = ""
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
            categories = listOf(CategoryScreenElements(Category(), listOf(screenElement))),
            selectedCategoryIndex = 0
        )
        state.value = initialState

        reducer.invoke(0)

        state.value shouldBeEqualTo initialState.copy(
            selectedElementIndex = 0,
            fileNameRendered = "test.kt",
            sampleCode = "test"
        )
    }
}
