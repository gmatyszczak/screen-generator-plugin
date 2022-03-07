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
import ui.settings.SettingsAction.ChangeCategoryName
import ui.settings.SettingsState

class ChangeCategoryNameReducerTest {

    val state = MutableStateFlow(SettingsState())
    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var reducer: ChangeCategoryNameReducer

    @BeforeEach
    fun setup() {
        reducer = ChangeCategoryNameReducer(state, actionFlow)
    }

    @Test
    fun `if selected element not null on invoke`() = runBlockingTest {
        state.value = SettingsState(
            categories = listOf(
                CategoryScreenElements(
                    Category(),
                    listOf(ScreenElement())
                )
            ),
            selectedCategoryIndex = 0
        )

        actionFlow.test {
            reducer.invoke(ChangeCategoryName("test"))

            awaitItem() shouldBeEqualTo SettingsAction.UpdateCategory(Category(name = "test"))
            cancelAndIgnoreRemainingEvents()
        }
    }
}
