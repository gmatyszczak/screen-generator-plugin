package ui.settings.reducer

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class RemoveScreenElementReducerImplTest : BaseReducerTest() {

    val selectScreenElementReducerMock: SelectScreenElementReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: RemoveScreenElementReducerImpl

    val initialState = SettingsState(
        categories = listOf(
            CategoryScreenElements(
                Category(),
                listOf(
                    ScreenElement(name = "test1"),
                    ScreenElement(name = "test2")
                )
            )
        ),
        selectedCategoryIndex = 0
    )

    @BeforeEach
    fun setup() {
        state.value = initialState
        reducer =
            RemoveScreenElementReducerImpl(state, effectMock, TestCoroutineScope(), selectScreenElementReducerMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke(0)

        assertEquals(
            SettingsState(
                isModified = true,
                categories = listOf(
                    CategoryScreenElements(
                        Category(),
                        listOf(
                            ScreenElement(name = "test2")
                        )
                    )
                ),
                selectedCategoryIndex = 0
            ),
            state.value
        )
        verify { selectScreenElementReducerMock.invoke(0) }
    }
}
