package ui.newscreen.reducer

import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.newscreen.NewScreenEffect
import ui.newscreen.NewScreenState

abstract class BaseReducerTest {

    protected val effectMock: MutableSharedFlow<NewScreenEffect> = mockk()

    protected val state = MutableStateFlow(NewScreenState())
}
