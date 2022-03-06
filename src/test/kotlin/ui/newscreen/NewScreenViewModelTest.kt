package ui.newscreen

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import model.Category
import model.Module
import org.junit.jupiter.api.Test
import ui.newscreen.reducer.CategoryIndexChangedReducer
import ui.newscreen.reducer.InitReducer
import ui.newscreen.reducer.OkClickedReducer

@ExperimentalCoroutinesApi
class NewScreenViewModelTest {

    val initReducerMock: InitReducer = mockk(relaxUnitFun = true)
    val okClickedReducerMock: OkClickedReducer = mockk(relaxUnitFun = true)
    val categoryIndexChangedReducerMock: CategoryIndexChangedReducer = mockk(relaxUnitFun = true)
    val viewModel = NewScreenViewModel(
        initReducerMock,
        okClickedReducerMock,
        categoryIndexChangedReducerMock,
    )

    @Test
    fun `on init`() {
        verify { initReducerMock.invoke() }
    }

    @Test
    fun `on OkClicked`() {
        viewModel.reduce(
            NewScreenAction.OkClicked(
                "test",
                "test",
                0,
                Module("", ""),
                Category(),
                emptyMap()
            )
        )

        verify { initReducerMock.invoke() }
        verify {
            okClickedReducerMock.invoke(
                "test",
                "test",
                0,
                Module("", ""),
                Category(),
                emptyMap()
            )
        }
    }

    @Test
    fun `on CategoryIndexChanged`() {
        viewModel.reduce(NewScreenAction.CategoryIndexChanged(100))

        verify { initReducerMock.invoke() }
        verify { categoryIndexChangedReducerMock.invoke(100) }
    }
}
