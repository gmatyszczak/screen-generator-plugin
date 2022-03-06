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
import ui.settings.SettingsState

class MoveDownCategoryReducerTest {

    val state = MutableStateFlow(SettingsState())
    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var reducer: MoveDownCategoryReducer

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
        selectedCategoryIndex = 0
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = MoveDownCategoryReducer(state, actionFlow)
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        actionFlow.test {
            reducer.invoke(SettingsAction.MoveDownCategory(0))

            state.value shouldBeEqualTo initialState.copy(
                categories = listOf(categoryScreenElement2, categoryScreenElement1),
                isModified = true
            )
            awaitItem() shouldBeEqualTo SettingsAction.SelectCategory(1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
