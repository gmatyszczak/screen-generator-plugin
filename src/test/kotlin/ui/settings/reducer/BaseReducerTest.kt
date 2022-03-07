package ui.settings.reducer

import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsAction
import ui.settings.SettingsEffect
import ui.settings.SettingsState

abstract class BaseReducerTest {

    protected val effectMock: MutableSharedFlow<SettingsEffect> = mockk()
    val state = MutableStateFlow(SettingsState())
    val actionFlow = MutableSharedFlow<SettingsAction>()
}
