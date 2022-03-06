package ui.settings.reducer

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.CustomVariable
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class MoveUpCustomVariableReducerImplTest : BaseReducerTest() {

    val selectCustomVariableReducerMock: SelectCustomVariableReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: MoveUpCustomVariableReducerImpl

    val categoryScreenElement = CategoryScreenElements(
        Category(customVariables = listOf(CustomVariable("test1"), CustomVariable("test2"))),
        listOf(ScreenElement(name = "test"))
    )
    val initialState = SettingsState(
        categories = listOf(categoryScreenElement),
        selectedCategoryIndex = 0
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer =
            MoveUpCustomVariableReducerImpl(state, effectMock, TestCoroutineScope(), selectCustomVariableReducerMock)
    }

    @Test
    fun `when selected category not null on invoke`() {
        reducer.invoke(1)

        assertEquals(
            SettingsState(
                selectedCategoryIndex = 0,
                isModified = true,
                categories = listOf(
                    CategoryScreenElements(
                        Category(
                            customVariables = listOf(
                                CustomVariable("test2"),
                                CustomVariable("test1")
                            )
                        ),
                        listOf(ScreenElement(name = "test"))
                    )
                )
            ),
            state.value
        )
        verify { selectCustomVariableReducerMock.invoke(0) }
    }

    @Test
    fun `when selected category null on invoke`() {
        state.value = SettingsState()

        reducer.invoke(1)

        assertEquals(
            SettingsState(),
            state.value
        )
        verify { selectCustomVariableReducerMock wasNot Called }
    }
}
