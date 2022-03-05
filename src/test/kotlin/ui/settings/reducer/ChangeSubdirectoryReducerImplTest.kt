package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState

class ChangeSubdirectoryReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var updateScreenElementReducerMock: UpdateScreenElementReducer

    private lateinit var reducer: ChangeSubdirectoryReducerImpl

    @Before
    fun setup() {
        reducer = ChangeSubdirectoryReducerImpl(state, effectMock, TestCoroutineScope(), updateScreenElementReducerMock)
    }

    @Test
    fun `if selected element not null on invoke`() {
        state.value = SettingsState(
            categories = listOf(
                CategoryScreenElements(
                    Category(),
                    listOf(ScreenElement())
                )
            ),
            selectedElementIndex = 0,
            selectedCategoryIndex = 0
        )

        reducer.invoke("test")

        verify(updateScreenElementReducerMock).invoke(ScreenElement(subdirectory = "test"))
    }

    @Test
    fun `if selected element null on invoke`() {
        reducer.invoke("test")

        verifyZeroInteractions(updateScreenElementReducerMock)
    }
}
