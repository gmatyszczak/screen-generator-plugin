package ui.settings.reducer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.CustomVariable
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import ui.settings.SettingsState
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class AddCustomVariableReducerImplTest : BaseReducerTest() {

    private lateinit var reducer: AddCustomVariableReducer

    private val categoryScreenElement = CategoryScreenElements(
        Category(customVariables = listOf(CustomVariable("test"))),
        listOf(ScreenElement(name = "test"))
    )
    private val initialState = SettingsState(
        categories = listOf(categoryScreenElement),
        selectedCategoryIndex = 0
    )

    @Before
    fun setup() {
        state.value = initialState
        reducer = AddCustomVariableReducerImpl(state, effectMock, TestCoroutineScope())
    }

    @Test
    fun `when selected category not null on invoke`() {
        reducer.invoke()

        assertEquals(
            SettingsState(
                selectedCategoryIndex = 0,
                isModified = true,
                categories = listOf(
                    CategoryScreenElements(
                        Category(
                            customVariables = listOf(
                                CustomVariable("test"),
                                CustomVariable.getDefault()
                            )
                        ), listOf(ScreenElement(name = "test"))
                    )
                )
            ),
            state.value
        )
    }

    @Test
    fun `when selected category null on invoke`() {
        state.value = SettingsState()

        reducer.invoke()

        assertEquals(
            SettingsState(),
            state.value
        )
    }
}