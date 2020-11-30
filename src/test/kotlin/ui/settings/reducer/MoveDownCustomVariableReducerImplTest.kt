package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.CustomVariable
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MoveDownCustomVariableReducerImplTest : BaseReducerTest() {

    private lateinit var reducer: MoveDownCustomVariableReducerImpl

    private val categoryScreenElement = CategoryScreenElements(
        Category(customVariables = listOf(CustomVariable("test1"), CustomVariable("test2"))),
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
        reducer =
            MoveDownCustomVariableReducerImpl(state, effectMock, TestCoroutineScope(), selectCustomVariableReducerMock)
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
                            customVariables = listOf(
                                CustomVariable("test2"),
                                CustomVariable("test1")
                            )
                        ), listOf(ScreenElement(name = "test"))
                    )
                )
            ),
            state.value
        )
        verify(selectCustomVariableReducerMock).invoke(1)
    }

    @Test
    fun `when selected category null on invoke`() {
        state.value = SettingsState()

        reducer.invoke(0)

        assertEquals(
            SettingsState(),
            state.value
        )
        verifyZeroInteractions(selectCustomVariableReducerMock)
    }
}