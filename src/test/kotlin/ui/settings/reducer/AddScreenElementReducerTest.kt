package ui.settings.reducer

import app.cash.turbine.test
import io.mockk.every
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
import ui.settings.SettingsAction.AddScreenElement
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class AddScreenElementReducerTest {

    val screenElement: ScreenElement = mockk()
    val category: Category = mockk {
        every { id } returns 1
    }
    val categoryScreenElement1: CategoryScreenElements = mockk()
    val categoryScreenElement2 = CategoryScreenElements(
        category,
        listOf(screenElement),
    )
    val state = MutableStateFlow(SettingsState())
    val actionFlow = MutableSharedFlow<SettingsAction>()
    val reducer = AddScreenElementReducer(state, actionFlow)

    @Test
    fun `on invoke`() = runBlockingTest {
        actionFlow.test {
            state.value = SettingsState(
                categories = listOf(categoryScreenElement1, categoryScreenElement2),
                selectedCategoryIndex = 1
            )

            reducer.invoke(AddScreenElement)

            state.value shouldBeEqualTo SettingsState(
                isModified = true,
                categories = listOf(
                    categoryScreenElement1,
                    CategoryScreenElements(
                        category = category,
                        screenElements = listOf(screenElement, ScreenElement.getDefault(1))
                    ),
                ),
                selectedCategoryIndex = 1
            )
            awaitItem() shouldBeEqualTo SettingsAction.SelectScreenElement(1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
