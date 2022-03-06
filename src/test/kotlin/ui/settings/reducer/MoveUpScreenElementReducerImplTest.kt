package ui.settings.reducer

import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsEffect
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class MoveUpScreenElementReducerImplTest : BaseReducerTest() {

    val selectScreenElementReducerMock: SelectScreenElementReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: MoveUpScreenElementReducerImpl

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
            MoveUpScreenElementReducerImpl(state, effectMock, TestCoroutineScope(), selectScreenElementReducerMock)
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        reducer.invoke(1)

        state.value shouldBeEqualTo SettingsState(
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
        )
        coVerify { effectMock.emit(SettingsEffect.SelectScreenElement(0)) }
        verify { selectScreenElementReducerMock.invoke(0) }
    }
}
