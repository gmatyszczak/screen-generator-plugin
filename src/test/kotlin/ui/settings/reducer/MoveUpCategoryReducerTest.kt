package ui.settings.reducer

import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import model.CategoryScreenElements
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction
import ui.settings.SettingsState

class MoveUpCategoryReducerTest {

    val categoryScreenElement1: CategoryScreenElements = mockk()
    val categoryScreenElement2: CategoryScreenElements = mockk()
    val state = MutableStateFlow(SettingsState())
    val reducer = MoveUpCategoryReducer(state)

    @Test
    fun `when index is not first index on invoke`() {
        state.value = SettingsState(
            categories = listOf(categoryScreenElement1, categoryScreenElement2),
            selectedCategoryIndex = 1,
        )

        reducer.invoke(SettingsAction.MoveUpCategory(1))

        state.value shouldBeEqualTo SettingsState(
            categories = listOf(categoryScreenElement2, categoryScreenElement1),
            isModified = true,
            selectedCategoryIndex = 0,
        )
    }

    @Test
    fun `when index is first index on invoke`() {
        state.value = SettingsState(
            categories = listOf(categoryScreenElement1, categoryScreenElement2),
            selectedCategoryIndex = 0,
        )

        reducer.invoke(SettingsAction.MoveUpCategory(0))

        state.value shouldBeEqualTo SettingsState(
            categories = listOf(categoryScreenElement1, categoryScreenElement2),
            selectedCategoryIndex = 0,
        )
    }
}
