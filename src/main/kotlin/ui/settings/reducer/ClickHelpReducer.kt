package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsEffect
import javax.inject.Inject

class ClickHelpReducer @Inject constructor(
    private val effect: MutableSharedFlow<SettingsEffect>,
) : Reducer.Suspend<SettingsAction.ClickHelp> {

    override suspend fun invoke(action: SettingsAction.ClickHelp) {
        effect.emit(SettingsEffect.ShowHelp)
    }
}
