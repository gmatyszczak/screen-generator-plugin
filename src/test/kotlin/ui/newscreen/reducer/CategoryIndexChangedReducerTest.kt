package ui.newscreen.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import model.Category
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.newscreen.NewScreenAction.CategoryIndexChanged
import ui.newscreen.NewScreenState

class CategoryIndexChangedReducerTest {

    val state = MutableStateFlow(NewScreenState())
    lateinit var reducer: CategoryIndexChangedReducer

    @BeforeEach
    fun setUp() {
        reducer = CategoryIndexChangedReducer(state)
    }

    @Test
    fun `on invoke`() {
        state.value = NewScreenState(categories = listOf(Category(), Category(name = "test")))

        reducer(CategoryIndexChanged(1))

        state.value shouldBeEqualTo NewScreenState(
            categories = listOf(Category(), Category(name = "test")),
            selectedCategory = Category(name = "test")
        )
    }
}
