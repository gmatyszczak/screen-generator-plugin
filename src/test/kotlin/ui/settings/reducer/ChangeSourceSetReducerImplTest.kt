package ui.settings.reducer

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import ui.settings.SettingsState

class ChangeSourceSetReducerImplTest : BaseReducerTest() {

    val updateScreenElementReducerMock: UpdateScreenElementReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: ChangeSourceSetReducerImpl

    @Before
    fun setup() {
        reducer = ChangeSourceSetReducerImpl(state, effectMock, TestCoroutineScope(), updateScreenElementReducerMock)
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

        verify {updateScreenElementReducerMock.invoke(ScreenElement(sourceSet = "test")) }
    }

    @Test
    fun `if selected element null on invoke`() {
        reducer.invoke("test")

        verify { updateScreenElementReducerMock wasNot Called }
    }
}
