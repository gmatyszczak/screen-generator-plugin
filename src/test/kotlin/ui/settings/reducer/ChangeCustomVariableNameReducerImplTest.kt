package ui.settings.reducer

import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.CustomVariable
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

class ChangeCustomVariableNameReducerImplTest : BaseReducerTest() {

    lateinit var reducer: ChangeCustomVariableNameReducerImpl

    @BeforeEach
    fun setup() {
        reducer = ChangeCustomVariableNameReducerImpl(state, effectMock, TestCoroutineScope())
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

        reducer.invoke("test2")

        assertEquals(
            SettingsState(
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
            ),
            state.value
        )
    }

    @Test
    fun `if selected element null on invoke`() {
        reducer.invoke("test")

        assertEquals(SettingsState(), state.value)
    }
}
