package ui.settings.reducer

import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import model.CategoryScreenElements
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction
import ui.settings.SettingsState

class RemoveCategoryReducerTest {

    val categoryScreenElement1: CategoryScreenElements = mockk()
    val categoryScreenElement2: CategoryScreenElements = mockk()
    val state = MutableStateFlow(SettingsState())
    val reducer = RemoveCategoryReducer(state)

    @Test
    fun `on invoke`() {
        state.value = SettingsState(
            categories = listOf(categoryScreenElement1, categoryScreenElement2),
            selectedCategoryIndex = 0,
            selectedElementIndex = 0,
            selectedCustomVariableIndex = 0,
        )

        reducer.invoke(SettingsAction.RemoveCategory(0))

        state.value shouldBeEqualTo SettingsState(
            isModified = true,
            categories = listOf(categoryScreenElement2),
            selectedCategoryIndex = null,
            selectedElementIndex = null,
            selectedCustomVariableIndex = null,
        )
    }
}
