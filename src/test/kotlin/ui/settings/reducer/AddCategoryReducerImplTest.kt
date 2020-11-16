package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class AddCategoryReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var selectScreenElementReducerMock: SelectScreenElementReducer

    private lateinit var reducer: AddCategoryReducerImpl

    private val initialState = SettingsState(
        screenElements = listOf(
            ScreenElement(name = "test")
        ),
        categories = listOf(Category())
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
                screenElements = emptyList(),
                selectedElementIndex = null,
                categories = listOf(Category(), Category.getDefault(1)),
                selectedCategoryIndex = 1
            ),
            state.value
        )
        verify(selectScreenElementReducerMock).invoke(-1)
    }
}