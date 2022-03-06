package ui.settings.reducer

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.TestCoroutineScope
import model.AndroidComponent
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

class ChangeAndroidComponentReducerImplTest : BaseReducerTest() {

    val updateScreenElementReducerMock: UpdateScreenElementReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: ChangeAndroidComponentReducerImpl

    @BeforeEach
    fun setup() {
        reducer =
            ChangeAndroidComponentReducerImpl(state, effectMock, TestCoroutineScope(), updateScreenElementReducerMock)
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

        reducer.invoke(AndroidComponent.FRAGMENT.ordinal)

        verify { updateScreenElementReducerMock.invoke(ScreenElement(relatedAndroidComponent = AndroidComponent.FRAGMENT)) }
    }

    @Test
    fun `if selected element null on invoke`() {
        reducer.invoke(AndroidComponent.ACTIVITY.ordinal)

        verify { updateScreenElementReducerMock wasNot Called }
    }
}
