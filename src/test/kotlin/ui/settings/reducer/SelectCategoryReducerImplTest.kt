package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsEffect
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class SelectCategoryReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var selectScreenElementReducerMock: SelectScreenElementReducer

    @Mock
    private lateinit var selectCustomVariableReducerMock: SelectCustomVariableReducer

    private lateinit var reducer: SelectCategoryReducerImpl

    private val categoryScreenElement = CategoryScreenElements(
        Category(),
        listOf(ScreenElement(name = "test"))
    )
    private val initialState = SettingsState(
        categories = listOf(categoryScreenElement)
    )

    @Before
    fun setup() {
        state.value = initialState
        reducer = SelectCategoryReducerImpl(
            state,
            effectMock,
            TestCoroutineScope(),
            selectScreenElementReducerMock,
            selectCustomVariableReducerMock
        )
    }

    @Test
    fun `when index out of bounds on invoke`() {
        reducer.invoke(10)

        assertEquals(
            initialState.copy(
                selectedCategoryIndex = null
            ),
            state.value
        )
        verify(selectScreenElementReducerMock).invoke(-1)
        verify(selectCustomVariableReducerMock).invoke(-1)
    }

    @Test
    fun `when index in  bounds on invoke`() = runBlockingTest {
        reducer.invoke(0)

        assertEquals(
            initialState.copy(
                selectedCategoryIndex = 0
            ),
            state.value
        )
        verify(selectScreenElementReducerMock).invoke(0)
        verify(selectCustomVariableReducerMock).invoke(-1)
        verify(effectMock).emit(SettingsEffect.SelectScreenElement(0))
    }
}
