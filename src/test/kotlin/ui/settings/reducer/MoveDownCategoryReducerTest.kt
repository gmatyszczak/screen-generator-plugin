package ui.settings.reducer

import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import model.CategoryScreenElements
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction
import ui.settings.SettingsState

class MoveDownCategoryReducerTest {

    val categoryScreenElement1: CategoryScreenElements = mockk()
    val categoryScreenElement2: CategoryScreenElements = mockk()
    val state = MutableStateFlow(SettingsState())
    val reducer: MoveDownCategoryReducer = MoveDownCategoryReducer(state)

    @Test
    fun `when index is not last index on invoke`() {
        state.value = SettingsState(
            categories = listOf(categoryScreenElement1, categoryScreenElement2),
            selectedCategoryIndex = 0,
        )

        reducer.invoke(SettingsAction.MoveDownCategory(0))

        state.value shouldBeEqualTo SettingsState(
            categories = listOf(categoryScreenElement2, categoryScreenElement1),
            isModified = true,
            selectedCategoryIndex = 1,
        )
    }

    @Test
    fun `when index is last index on invoke`() {
        state.value = SettingsState(
            categories = listOf(categoryScreenElement1, categoryScreenElement2),
            selectedCategoryIndex = 1,
        )

        reducer.invoke(SettingsAction.MoveDownCategory(1))

        state.value shouldBeEqualTo SettingsState(
            categories = listOf(categoryScreenElement1, categoryScreenElement2),
            selectedCategoryIndex = 1,
        )
    }
}
