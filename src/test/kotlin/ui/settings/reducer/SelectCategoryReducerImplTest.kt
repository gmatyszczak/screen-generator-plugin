package ui.settings.reducer

import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ui.settings.SettingsEffect
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class SelectCategoryReducerImplTest : BaseReducerTest() {

    val selectScreenElementReducerMock: SelectScreenElementReducer = mockk(relaxUnitFun = true)
    val selectCustomVariableReducerMock: SelectCustomVariableReducer = mockk(relaxUnitFun = true)

    lateinit var reducer: SelectCategoryReducerImpl

    val categoryScreenElement = CategoryScreenElements(
        Category(),
        listOf(ScreenElement(name = "test"))
    )
    val initialState = SettingsState(
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
        verify { selectScreenElementReducerMock.invoke(-1) }
        verify { selectCustomVariableReducerMock.invoke(-1) }
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
        verify { selectScreenElementReducerMock.invoke(0) }
        verify { selectCustomVariableReducerMock.invoke(-1) }
        coVerify { effectMock.emit(SettingsEffect.SelectScreenElement(0)) }
    }
}
