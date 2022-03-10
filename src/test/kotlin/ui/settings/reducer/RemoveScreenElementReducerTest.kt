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
import ui.settings.SettingsAction.RemoveScreenElement
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class RemoveScreenElementReducerTest {

    val state = MutableStateFlow(SettingsState())
    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var reducer: RemoveScreenElementReducer

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
        selectedCategoryIndex = 0,
        selectedElementIndex = 0,
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = RemoveScreenElementReducer(state, actionFlow)
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        actionFlow.test {
            reducer.invoke(RemoveScreenElement(0))

            state.value shouldBeEqualTo SettingsState(
                isModified = true,
                categories = listOf(
                    CategoryScreenElements(
                        Category(),
                        listOf(
                            ScreenElement(name = "test2")
                        )
                    )
                ),
                selectedCategoryIndex = 0,
                selectedElementIndex = null,
            )
            awaitItem() shouldBeEqualTo SettingsAction.SelectScreenElement(0)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
