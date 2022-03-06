package ui.settings.reducer

import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState

abstract class BaseReducerTest {

    protected val effectMock: MutableSharedFlow<SettingsEffect> = mockk()
    protected val state = MutableStateFlow(SettingsState())
}
