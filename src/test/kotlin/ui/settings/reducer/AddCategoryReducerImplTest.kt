package ui.settings.reducer

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class AddCategoryReducerImplTest : BaseReducerTest() {

    val selectScreenElementReducerMock: SelectScreenElementReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: AddCategoryReducerImpl

    val categoryScreenElement = CategoryScreenElements(
        Category(),
        listOf(ScreenElement(name = "test"))
    )
    val initialState = SettingsState(
        categories = listOf(categoryScreenElement)
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = AddCategoryReducerImpl(state, effectMock, TestCoroutineScope(), selectScreenElementReducerMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke()

        state.value shouldBeEqualTo SettingsState(
            isModified = true,
            categories = listOf(
                categoryScreenElement, CategoryScreenElements(Category.getDefault(1), emptyList())
            ),
            selectedElementIndex = null,
            selectedCategoryIndex = 1
        )
        verify { selectScreenElementReducerMock.invoke(-1) }
    }
}
