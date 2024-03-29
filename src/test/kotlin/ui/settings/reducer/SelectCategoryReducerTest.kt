package ui.settings.reducer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction.SelectCategory
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class SelectCategoryReducerTest {

    val state = MutableStateFlow(SettingsState())

    lateinit var reducer: SelectCategoryReducer

    val categoryScreenElement = CategoryScreenElements(
        Category(),
        listOf(ScreenElement(name = "test"))
    )
    val initialState = SettingsState(
        categories = listOf(categoryScreenElement)
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = SelectCategoryReducer(state)
    }

    @Test
    fun `when index out of bounds on invoke`() = runBlockingTest {
        reducer.invoke(SelectCategory(10))

        state.value shouldBeEqualTo initialState.copy(
            selectedCategoryIndex = null,
            selectedElementIndex = null,
        )
    }

    @Test
    fun `when index in bounds on invoke`() = runBlockingTest {
        reducer.invoke(SelectCategory(0))

        state.value shouldBeEqualTo initialState.copy(
            selectedCategoryIndex = 0,
            selectedElementIndex = 0,
        )
    }
}
