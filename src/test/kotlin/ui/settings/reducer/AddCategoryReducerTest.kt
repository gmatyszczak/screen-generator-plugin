package ui.settings.reducer

import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction.AddCategory
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class AddCategoryReducerTest {

    val state = MutableStateFlow(SettingsState())
    lateinit var reducer: AddCategoryReducer

    val categoryScreenElement: CategoryScreenElements = mockk()
    val initialState = SettingsState(
        isModified = false,
        categories = listOf(categoryScreenElement),
        selectedCategoryIndex = 0,
        selectedElementIndex = 0,
        selectedCustomVariableIndex = 0,
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer = AddCategoryReducer(state)
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        reducer.invoke(AddCategory)

        state.value shouldBeEqualTo SettingsState(
            isModified = true,
            categories = listOf(
                categoryScreenElement,
                CategoryScreenElements(
                    Category.getDefault(1),
                    emptyList()
                ),
            ),
            selectedCategoryIndex = 1,
            selectedElementIndex = null,
            selectedCustomVariableIndex = null,
        )
    }
}
