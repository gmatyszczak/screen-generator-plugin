package ui.settings.reducer

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ui.settings.SettingsState

class MoveUpCategoryReducerImplTest : BaseReducerTest() {

    val selectCategoryReducerMock: SelectCategoryReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: MoveUpCategoryReducerImpl

    val categoryScreenElement1 = CategoryScreenElements(
        Category(name = "test1"),
        listOf(ScreenElement(name = "test1"))
    )

    val categoryScreenElement2 = CategoryScreenElements(
        Category(name = "test2"),
        listOf(ScreenElement(name = "test2"))
    )

    val initialState = SettingsState(
        categories = listOf(categoryScreenElement1, categoryScreenElement2),
        selectedCategoryIndex = 1
    )

    @Before
    fun setup() {
        state.value = initialState
        reducer = MoveUpCategoryReducerImpl(state, effectMock, TestCoroutineScope(), selectCategoryReducerMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke(1)

        assertEquals(
            initialState.copy(
                categories = listOf(categoryScreenElement2, categoryScreenElement1),
                isModified = true
            ),
            state.value
        )

        verify { selectCategoryReducerMock.invoke(0) }
    }
}
