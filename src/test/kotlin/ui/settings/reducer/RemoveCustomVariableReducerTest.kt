package ui.settings.reducer

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import model.CustomVariable
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class RemoveCustomVariableReducerTest {

    val state = MutableStateFlow(SettingsState())
    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var reducer: RemoveCustomVariableReducer

    val categoryScreenElement = CategoryScreenElements(
        Category(customVariables = listOf(CustomVariable("test"))),
        listOf(ScreenElement(name = "test"))
    )

    val initialState = SettingsState(
        categories = listOf(categoryScreenElement),
        selectedCategoryIndex = 0
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = RemoveCustomVariableReducer(state, actionFlow)
    }

    @Test
    fun `when selected category not null on invoke`() = runBlockingTest {
        actionFlow.test {
            reducer.invoke(SettingsAction.RemoveCustomVariable(0))

            state.value shouldBeEqualTo SettingsState(
                selectedCategoryIndex = 0,
                isModified = true,
                categories = listOf(
                    CategoryScreenElements(
                        Category(
                            customVariables = emptyList()
                        ),
                        listOf(ScreenElement(name = "test"))
                    )
                )
            )
            awaitItem() shouldBeEqualTo SettingsAction.SelectCustomVariable(0)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
