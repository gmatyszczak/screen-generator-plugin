package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class AddScreenElementReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var selectScreenElementReducerMock: SelectScreenElementReducer

    private lateinit var reducer: AddScreenElementReducerImpl

    private val categoryScreenElement = CategoryScreenElements(
        Category(),
        listOf(ScreenElement(name = "test"))
    )
    private val initialState = SettingsState(
        categories = listOf(categoryScreenElement),
        selectedCategoryIndex = 0
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
                    categoryScreenElement.copy(
                        screenElements = listOf(ScreenElement(name = "test"), ScreenElement.getDefault())
                    )
                ),
                selectedCategoryIndex = 0
            ),
            state.value
        )
        verify(selectScreenElementReducerMock).invoke(1)
    }
}