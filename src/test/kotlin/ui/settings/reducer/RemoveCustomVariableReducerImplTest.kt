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
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class RemoveCustomVariableReducerImplTest : BaseReducerTest() {

    val selectCustomVariableReducerMock: SelectCustomVariableReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: RemoveCustomVariableReducerImpl

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
        reducer =
            RemoveCustomVariableReducerImpl(state, effectMock, TestCoroutineScope(), selectCustomVariableReducerMock)
    }

    @Test
    fun `when selected category not null on invoke`() {
        reducer.invoke(0)

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
        verify { selectCustomVariableReducerMock.invoke(0) }
    }

    @Test
    fun `when selected category null on invoke`() {
        state.value = SettingsState()

        reducer.invoke(10)

        state.value shouldBeEqualTo SettingsState()
        verify { selectCustomVariableReducerMock wasNot Called }
    }
}
