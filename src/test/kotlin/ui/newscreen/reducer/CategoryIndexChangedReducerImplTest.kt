package ui.newscreen.reducer

import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.newscreen.NewScreenState

class CategoryIndexChangedReducerImplTest : BaseReducerTest() {

    private lateinit var reducer: CategoryIndexChangedReducerImpl

    @BeforeEach
    fun setUp() {
        reducer = CategoryIndexChangedReducerImpl(
            state,
            effectMock,
            TestCoroutineScope()
        )
    }

    @Test
    fun `on invoke`() {
        state.value = NewScreenState(categories = listOf(Category(), Category(name = "test")))

        reducer(1)

        assertEquals(
            NewScreenState(
                categories = listOf(Category(), Category(name = "test")),
                selectedCategory = Category(name = "test")
            ),
            state.value
        )
    }
}
