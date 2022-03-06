package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsEffect
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class MoveUpScreenElementReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var selectScreenElementReducerMock: SelectScreenElementReducer

    private lateinit var reducer: MoveUpScreenElementReducerImpl

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
        reducer =
            MoveUpScreenElementReducerImpl(state, effectMock, TestCoroutineScope(), selectScreenElementReducerMock)
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        reducer.invoke(1)

        assertEquals(
            SettingsState(
                isModified = true,
                categories = listOf(
                    CategoryScreenElements(
                        Category(),
                        listOf(
                            ScreenElement(name = "test2"),
                            ScreenElement(name = "test1")
                        )
                    )
                ),
                selectedCategoryIndex = 0
            ),
            state.value
        )
        verify(effectMock).emit(SettingsEffect.SelectScreenElement(0))
        verify(selectScreenElementReducerMock).invoke(0)
    }
}
