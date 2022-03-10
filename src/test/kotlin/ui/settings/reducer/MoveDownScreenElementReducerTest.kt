package ui.settings.reducer

import app.cash.turbine.test
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction
import ui.settings.SettingsAction.MoveDownScreenElement
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class MoveDownScreenElementReducerTest {

    val category: Category = mockk()
    val screenElement1: ScreenElement = mockk()
    val screenElement2: ScreenElement = mockk()
    val state = MutableStateFlow(SettingsState())
    val actionFlow = MutableSharedFlow<SettingsAction>()
    val reducer = MoveDownScreenElementReducer(state, actionFlow)

    @Test
    fun `on invoke`() = runBlockingTest {
        actionFlow.test {
            state.value = SettingsState(
                categories = listOf(
                    CategoryScreenElements(
                        category,
                        listOf(screenElement1, screenElement2),
                    )
                ),
                selectedCategoryIndex = 0,
            )

            reducer.invoke(MoveDownScreenElement(0))

            state.value shouldBeEqualTo SettingsState(
                isModified = true,
                categories = listOf(
                    CategoryScreenElements(
                        category,
                        listOf(screenElement2, screenElement1),
                    )
                ),
                selectedCategoryIndex = 0
            )
            awaitItem() shouldBeEqualTo SettingsAction.SelectScreenElement(1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
