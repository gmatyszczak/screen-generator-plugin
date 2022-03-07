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
import ui.settings.SettingsAction.SelectCategory
import ui.settings.SettingsEffect
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class SelectCategoryReducerTest {

    val state = MutableStateFlow(SettingsState())
    val effect = MutableSharedFlow<SettingsEffect>()
    val actionFlow = MutableSharedFlow<SettingsAction>()

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
        reducer = SelectCategoryReducer(state, effect, actionFlow)
    }

    @Test
    fun `when index out of bounds on invoke`() = runBlockingTest {
        actionFlow.test {
            reducer.invoke(SelectCategory(10))

            state.value shouldBeEqualTo initialState.copy(
                selectedCategoryIndex = null
            )
            awaitItem() shouldBeEqualTo SettingsAction.SelectCustomVariable(-1)
            awaitItem() shouldBeEqualTo SettingsAction.SelectScreenElement(-1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when index in bounds on invoke`() = runBlockingTest {
        actionFlow.test {
            effect.test {
                reducer.invoke(SelectCategory(0))

                state.value shouldBeEqualTo initialState.copy(
                    selectedCategoryIndex = 0
                )
                awaitItem() shouldBeEqualTo SettingsEffect.SelectScreenElement(0)
                cancelAndIgnoreRemainingEvents()
            }
            awaitItem() shouldBeEqualTo SettingsAction.SelectCustomVariable(-1)
            awaitItem() shouldBeEqualTo SettingsAction.SelectScreenElement(0)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
