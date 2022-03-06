package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.settings.SettingsEffect
import ui.settings.SettingsState

abstract class BaseReducer(
    private val state: MutableStateFlow<SettingsState>,
    private val effect: MutableSharedFlow<SettingsEffect>,
    private val scope: CoroutineScope
) {

    protected fun pushEffect(newEffect: SettingsEffect) {
        scope.launch { effect.emit(newEffect) }
    }

    protected fun pushState(transform: SettingsState.() -> SettingsState) {
        state.value = transform(state.value)
    }
}
