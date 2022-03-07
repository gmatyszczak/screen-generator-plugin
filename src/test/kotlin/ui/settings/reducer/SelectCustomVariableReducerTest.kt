package ui.settings.reducer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import model.Category
import model.CategoryScreenElements
import model.CustomVariable
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction.SelectCustomVariable
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class SelectCustomVariableReducerTest {

    val state = MutableStateFlow(SettingsState())
    lateinit var reducer: SelectCustomVariableReducer

    @BeforeEach
    fun setup() {
        reducer = SelectCustomVariableReducer(state)
    }

    @Test
    fun `when custom variables empty on invoke`() {
        reducer.invoke(SelectCustomVariable(0))

        state.value shouldBeEqualTo SettingsState(
            selectedCustomVariableIndex = null,
        )
    }

    @Test
    fun `when index not in bounds on invoke`() {
        val initialState = SettingsState(
            categories = listOf(CategoryScreenElements(Category(), emptyList())),
            selectedCategoryIndex = 0
        )
        state.value = initialState

        reducer.invoke(SelectCustomVariable(10))

        state.value shouldBeEqualTo initialState.copy(
            selectedCustomVariableIndex = null,
        )
    }

    @Test
    fun `when index in bounds on invoke`() {
        val initialState = SettingsState(
            categories = listOf(
                CategoryScreenElements(
                    Category(customVariables = listOf(CustomVariable("test"))),
                    emptyList()
                )
            ),
            selectedCategoryIndex = 0
        )
        state.value = initialState

        reducer.invoke(SelectCustomVariable(0))

        state.value shouldBeEqualTo initialState.copy(
            selectedCustomVariableIndex = 0
        )
    }
}
