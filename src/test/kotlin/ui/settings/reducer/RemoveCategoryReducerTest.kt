package ui.settings.reducer

import app.cash.turbine.test
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
import ui.settings.SettingsEffect
import ui.settings.SettingsState

class RemoveCategoryReducerTest {

    val state = MutableStateFlow(SettingsState())
    val effect = MutableSharedFlow<SettingsEffect>()
    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var reducer: RemoveCategoryReducer

    val categoryScreenElement = CategoryScreenElements(
        Category(),
        listOf(ScreenElement(name = "test"))
    )
    val initialState = SettingsState(
        categories = listOf(categoryScreenElement.copy(category = Category(name = "test")), categoryScreenElement),
        selectedCategoryIndex = 0,
        selectedElementIndex = 0,
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = RemoveCategoryReducer(state, effect, actionFlow)
    }

    @Test
    fun `on invoke`() = runBlockingTest() {
        actionFlow.test {
            effect.test {
                reducer.invoke(SettingsAction.RemoveCategory(0))

                state.value shouldBeEqualTo initialState.copy(
                    categories = listOf(categoryScreenElement),
                    selectedCategoryIndex = null,
                    selectedElementIndex = null,
                    isModified = true
                )
                awaitItem() shouldBeEqualTo SettingsEffect.SelectScreenElement(-1)
                cancelAndIgnoreRemainingEvents()
            }
            awaitItem() shouldBeEqualTo SettingsAction.SelectCategory(0)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
