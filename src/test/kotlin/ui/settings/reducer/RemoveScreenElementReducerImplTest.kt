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
class RemoveScreenElementReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var selectScreenElementReducerMock: SelectScreenElementReducer

    private lateinit var reducer: RemoveScreenElementReducerImpl

    private val initialState = SettingsState(
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

    @Before
    fun setup() {
        state.value = initialState
        reducer = RemoveScreenElementReducerImpl(state, effectMock, TestCoroutineScope(), selectScreenElementReducerMock)
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
        verify(selectScreenElementReducerMock).invoke(0)
    }
}