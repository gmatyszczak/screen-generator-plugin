package ui.settings.reducer

import app.cash.turbine.test
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState

internal class ChangeAnchorNameReducerTest {

    val state = MutableStateFlow(SettingsState())
    val actionFlow = MutableSharedFlow<SettingsAction>()
    val reducer = ChangeAnchorNameReducer(state, actionFlow)

    @Test
    fun `if selected element not null on invoke`() = runBlockingTest {
        state.value = SettingsState(
            categories = listOf(
                CategoryScreenElements(
                    Category(),
                    listOf(ScreenElement())
                )
            ),
            selectedElementIndex = 0,
            selectedCategoryIndex = 0
        )

        actionFlow.test {
            reducer.invoke(SettingsAction.ChangeAnchorName("test"))

            awaitItem() shouldBeEqualTo UpdateScreenElement(ScreenElement(anchorName = "test"))
            cancelAndIgnoreRemainingEvents()
        }
    }
}
