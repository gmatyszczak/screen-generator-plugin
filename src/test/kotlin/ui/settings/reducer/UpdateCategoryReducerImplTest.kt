package ui.settings.reducer

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
class UpdateCategoryReducerImplTest : BaseReducerTest() {

    lateinit var reducer: UpdateCategoryReducerImpl

    @BeforeEach
    fun setup() {
        reducer = UpdateCategoryReducerImpl(state, effectMock, TestCoroutineScope())
    }

    @Test
    fun `on invoke`() {
        val initialState = SettingsState(
            categories = listOf(
                CategoryScreenElements(
                    Category(name = "test1"),
                    listOf(ScreenElement())
                )
            ),
            selectedCategoryIndex = 0
        )
        val updatedCategory = Category(name = "test2")

        state.value = initialState
        reducer.invoke(updatedCategory)

        state.value shouldBeEqualTo initialState.copy(
            categories = listOf(
                CategoryScreenElements(
                    updatedCategory,
                    listOf(ScreenElement())
                )
            ),
            isModified = true
        )
    }
}
