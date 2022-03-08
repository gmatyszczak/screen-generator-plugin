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

class MoveUpCategoryReducerTest {

    val state = MutableStateFlow(SettingsState())
    val effect = MutableSharedFlow<SettingsEffect>()
    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var reducer: MoveUpCategoryReducer

    val categoryScreenElement1 = CategoryScreenElements(
        Category(name = "test1"),
        listOf(ScreenElement(name = "test1"))
    )

    val categoryScreenElement2 = CategoryScreenElements(
        Category(name = "test2"),
        listOf(ScreenElement(name = "test2"))
    )

    val initialState = SettingsState(
        categories = listOf(categoryScreenElement1, categoryScreenElement2),
        selectedCategoryIndex = 1,
        selectedElementIndex = 0,
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = MoveUpCategoryReducer(state, effect, actionFlow)
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        actionFlow.test {
            effect.test {
                reducer.invoke(SettingsAction.MoveUpCategory(1))

                state.value shouldBeEqualTo initialState.copy(
                    categories = listOf(categoryScreenElement2, categoryScreenElement1),
                    isModified = true,
                    selectedCategoryIndex = 0,
                    selectedElementIndex = null,
                )
                awaitItem() shouldBeEqualTo SettingsEffect.SelectScreenElement(-1)
                cancelAndIgnoreRemainingEvents()
            }
            awaitItem() shouldBeEqualTo SettingsAction.SelectCategory(0)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
