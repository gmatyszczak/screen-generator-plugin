package ui.settings.reducer

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

class RemoveCategoryReducerImplTest : BaseReducerTest() {

    val selectCategoryReducerMock: SelectCategoryReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: RemoveCategoryReducerImpl

    val categoryScreenElement = CategoryScreenElements(
        Category(),
        listOf(ScreenElement(name = "test"))
    )
    val initialState = SettingsState(
        categories = listOf(categoryScreenElement.copy(category = Category(name = "test")), categoryScreenElement),
        selectedCategoryIndex = 0
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = RemoveCategoryReducerImpl(state, effectMock, TestCoroutineScope(), selectCategoryReducerMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke(0)

        assertEquals(
            initialState.copy(
                categories = listOf(categoryScreenElement),
                isModified = true
            ),
            state.value
        )

        verify { selectCategoryReducerMock.invoke(0) }
    }
}
