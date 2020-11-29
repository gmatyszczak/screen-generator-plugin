package ui.newscreen

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import model.Category
import model.Module
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import ui.newscreen.reducer.CategoryIndexChangedReducer
import ui.newscreen.reducer.InitReducer
import ui.newscreen.reducer.OkClickedReducer

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewScreenViewModelTest {

    @Mock
    private lateinit var initReducerMock: InitReducer

    @Mock
    private lateinit var okClickedReducerMock: OkClickedReducer

    @Mock
    private lateinit var categoryIndexChangedReducerMock: CategoryIndexChangedReducer

    @InjectMocks
    private lateinit var viewModel: NewScreenViewModel

    @Test
    fun `on init`() {
        verify(initReducerMock).invoke()
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

        verify(initReducerMock).invoke()
        verify(okClickedReducerMock).invoke(
            "test",
            "test",
            0,
            Module("", ""),
            Category(),
            emptyMap()
        )
    }

    @Test
    fun `on CategoryIndexChanged`() {
        viewModel.reduce(NewScreenAction.CategoryIndexChanged(100))

        verify(initReducerMock).invoke()
        verify(categoryIndexChangedReducerMock).invoke(100)
    }

}