package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class AddCategoryReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var selectScreenElementReducerMock: SelectScreenElementReducer

    private lateinit var reducer: AddCategoryReducerImpl

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
        reducer = AddCategoryReducerImpl(state, effectMock, TestCoroutineScope(), selectScreenElementReducerMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke()

        assertEquals(
            SettingsState(
                isModified = true,
                categories = listOf(
                    categoryScreenElement, CategoryScreenElements(Category.getDefault(1), emptyList())
                ),
                selectedElementIndex = null,
                selectedCategoryIndex = 1
            ),
            state.value
        )
        verify(selectScreenElementReducerMock).invoke(-1)
    }
}