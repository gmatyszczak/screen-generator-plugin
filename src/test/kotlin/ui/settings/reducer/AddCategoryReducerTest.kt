package ui.settings.reducer

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction
import ui.settings.SettingsAction.AddCategory
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class AddCategoryReducerTest {

    val state = MutableStateFlow(SettingsState())
    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var reducer: AddCategoryReducer

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
        reducer = AddCategoryReducer(state, actionFlow)
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        actionFlow.test {
            reducer.invoke(AddCategory)

            state.value shouldBeEqualTo SettingsState(
                isModified = true,
                categories = listOf(
                    categoryScreenElement, CategoryScreenElements(Category.getDefault(1), emptyList())
                ),
                selectedElementIndex = null,
                selectedCategoryIndex = 1
            )
            awaitItem() shouldBeEqualTo SettingsAction.SelectScreenElement(-1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
