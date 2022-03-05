package ui.newscreen.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import ui.newscreen.NewScreenEffect
import ui.newscreen.NewScreenState

@RunWith(MockitoJUnitRunner::class)
abstract class BaseReducerTest {

    @Mock
    protected lateinit var effectMock: MutableSharedFlow<NewScreenEffect>

    protected val state = MutableStateFlow(NewScreenState())
}
