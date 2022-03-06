package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState

class RemoveCategoryReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var selectCategoryReducerMock: SelectCategoryReducer

    private lateinit var reducer: RemoveCategoryReducerImpl

    private val categoryScreenElement = CategoryScreenElements(
        Category(),
        listOf(ScreenElement(name = "test"))
    )
    private val initialState = SettingsState(
        categories = listOf(categoryScreenElement.copy(category = Category(name = "test")), categoryScreenElement),
        selectedCategoryIndex = 0
    )

    @Before
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

        verify(selectCategoryReducerMock).invoke(0)
    }
}
