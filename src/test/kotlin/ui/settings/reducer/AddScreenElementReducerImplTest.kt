package ui.settings.reducer

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class AddScreenElementReducerImplTest : BaseReducerTest() {

    val selectScreenElementReducerMock: SelectScreenElementReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: AddScreenElementReducerImpl

    val categoryScreenElement1 = CategoryScreenElements(
        Category(id = 0),
        listOf(ScreenElement(name = "test"))
    )

    val categoryScreenElement2 = CategoryScreenElements(
        Category(id = 1),
        listOf(ScreenElement(name = "test"))
    )

    val initialState = SettingsState(
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
        verify { selectScreenElementReducerMock.invoke(1) }
    }
}
