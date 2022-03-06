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
import ui.settings.SettingsAction.AddScreenElement
import ui.settings.SettingsEffect
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class AddScreenElementReducerTest {

    val state = MutableStateFlow(SettingsState())
    val effect = MutableSharedFlow<SettingsEffect>()
    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var reducer: AddScreenElementReducer

    val categoryScreenElement1 = CategoryScreenElements(
        Category(id = 0),
        listOf(ScreenElement(name = "test"))
    )

    val categoryScreenElement2 = CategoryScreenElements(
        Category(id = 1),
        listOf(ScreenElement(name = "test"))
    )

    val initialState = SettingsState(
        categories = listOf(categoryScreenElement1, categoryScreenElement2),
        selectedCategoryIndex = 1
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = AddScreenElementReducer(state, effect, actionFlow)
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        effect.test {
            actionFlow.test {
                reducer.invoke(AddScreenElement)

                state.value shouldBeEqualTo SettingsState(
                    isModified = true,
                    categories = listOf(
                        categoryScreenElement1,
                        categoryScreenElement2.copy(
                            screenElements = listOf(ScreenElement(name = "test"), ScreenElement.getDefault(1))
                        )
                    ),
                    selectedCategoryIndex = 1
                )
                awaitItem() shouldBeEqualTo SettingsAction.SelectScreenElement(1)
                cancelAndIgnoreRemainingEvents()
            }
            awaitItem() shouldBeEqualTo SettingsEffect.SelectScreenElement(1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
