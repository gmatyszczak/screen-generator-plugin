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
class AddScreenElementReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var selectScreenElementReducerMock: SelectScreenElementReducer

    private lateinit var reducer: AddScreenElementReducerImpl

    private val categoryScreenElement1 = CategoryScreenElements(
        Category(id = 0),
        listOf(ScreenElement(name = "test"))
    )

    private val categoryScreenElement2 = CategoryScreenElements(
        Category(id = 1),
        listOf(ScreenElement(name = "test"))
    )

    private val initialState = SettingsState(
        categories = listOf(categoryScreenElement1, categoryScreenElement2),
        selectedCategoryIndex = 1
    )

    @Before
    fun setup() {
        state.value = initialState
        reducer = AddScreenElementReducerImpl(state, effectMock, TestCoroutineScope(), selectScreenElementReducerMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke()

        assertEquals(
            SettingsState(
                isModified = true,
                categories = listOf(
                    categoryScreenElement1,
                    categoryScreenElement2.copy(
                        screenElements = listOf(ScreenElement(name = "test"), ScreenElement.getDefault(1))
                    )
                ),
                selectedCategoryIndex = 1
            ),
            state.value
        )
        verify(selectScreenElementReducerMock).invoke(1)
    }
}
