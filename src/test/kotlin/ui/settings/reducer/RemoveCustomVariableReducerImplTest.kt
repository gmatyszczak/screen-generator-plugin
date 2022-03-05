package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.CustomVariable
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class RemoveCustomVariableReducerImplTest : BaseReducerTest() {

    private lateinit var reducer: RemoveCustomVariableReducerImpl

    private val categoryScreenElement = CategoryScreenElements(
        Category(customVariables = listOf(CustomVariable("test"))),
        listOf(ScreenElement(name = "test"))
    )
    private val initialState = SettingsState(
        categories = listOf(categoryScreenElement),
        selectedCategoryIndex = 0
    )

    @Mock
    private lateinit var selectCustomVariableReducerMock: SelectCustomVariableReducer

    @Before
    fun setup() {
        state.value = initialState
        reducer = RemoveCustomVariableReducerImpl(state, effectMock, TestCoroutineScope(), selectCustomVariableReducerMock)
    }

    @Test
    fun `when selected category not null on invoke`() {
        reducer.invoke(0)

        assertEquals(
            SettingsState(
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
            ),
            state.value
        )
        verify(selectCustomVariableReducerMock).invoke(0)
    }

    @Test
    fun `when selected category null on invoke`() {
        state.value = SettingsState()

        reducer.invoke(10)

        assertEquals(
            SettingsState(),
            state.value
        )
        verifyZeroInteractions(selectCustomVariableReducerMock)
    }
}
