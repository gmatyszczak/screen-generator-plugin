package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState

interface ClickHelpReducer {

    operator fun invoke()
}

class ClickHelpReducerImpl(
    state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope
) : BaseReducer(state, effect, scope), ClickHelpReducer {

    override fun invoke() = pushEffect(SettingsEffect.ShowHelp)
}
