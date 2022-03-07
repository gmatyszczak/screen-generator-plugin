package ui.settings.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import model.Category
import model.CategoryScreenElements
import model.CustomVariable
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction.ChangeCustomVariableName
import ui.settings.SettingsState

class ChangeCustomVariableNameReducerTest {

    val state = MutableStateFlow(SettingsState())
    lateinit var reducer: ChangeCustomVariableNameReducer

    @BeforeEach
    fun setup() {
        reducer = ChangeCustomVariableNameReducer(state)
    }

    @Test
    fun `if selected custom variable not null on invoke`() {
        state.value = SettingsState(
            categories = listOf(
                CategoryScreenElements(
                    Category(
                        customVariables = listOf(CustomVariable("test1"))
                    ),
                    listOf(ScreenElement())
                )
            ),
            selectedCategoryIndex = 0,
            selectedCustomVariableIndex = 0
        )

        reducer.invoke(ChangeCustomVariableName("test2"))

        state.value shouldBeEqualTo SettingsState(
            categories = listOf(
                CategoryScreenElements(
                    Category(
                        customVariables = listOf(CustomVariable("test2"))
                    ),
                    listOf(ScreenElement())
                )
            ),
            selectedCategoryIndex = 0,
            selectedCustomVariableIndex = 0,
            isModified = true
        )
    }

    @Test
    fun `if selected element null on invoke`() {
        reducer.invoke(ChangeCustomVariableName("test"))

        state.value shouldBeEqualTo SettingsState()
    }
}
