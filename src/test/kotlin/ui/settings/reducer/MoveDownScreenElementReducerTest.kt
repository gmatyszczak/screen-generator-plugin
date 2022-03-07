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
import ui.settings.SettingsAction.MoveDownScreenElement
import ui.settings.SettingsEffect
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class MoveDownScreenElementReducerTest {

    val state = MutableStateFlow(SettingsState())
    val effect = MutableSharedFlow<SettingsEffect>()
    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var reducer: MoveDownScreenElementReducer

    val initialState = SettingsState(
        categories = listOf(
            CategoryScreenElements(
                Category(),
                listOf(
                    ScreenElement(name = "test1"),
                    ScreenElement(name = "test2")
                )
            )
        ),
        selectedCategoryIndex = 0
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = MoveDownScreenElementReducer(state, effect, actionFlow)
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        actionFlow.test {
            effect.test {
                reducer.invoke(MoveDownScreenElement(0))

                state.value shouldBeEqualTo SettingsState(
                    isModified = true,
                    categories = listOf(
                        CategoryScreenElements(
                            Category(),
                            listOf(
                                ScreenElement(name = "test2"),
                                ScreenElement(name = "test1")
                            )
                        )
                    ),
                    selectedCategoryIndex = 0
                )
                awaitItem() shouldBeEqualTo SettingsEffect.SelectScreenElement(1)
                cancelAndIgnoreRemainingEvents()
            }
            awaitItem() shouldBeEqualTo SettingsAction.SelectScreenElement(1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
