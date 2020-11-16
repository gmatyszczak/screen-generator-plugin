package ui.settings.reducer

import data.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.Settings
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface ApplySettingsReducer {

    operator fun invoke()
}

class ApplySettingsReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val settingsRepository: SettingsRepository
) : BaseReducer(state, effect, scope), ApplySettingsReducer {

    override fun invoke() {
        val newSettings = state.value.run {
            Settings(screenElements = screenElements.toMutableList())
        }
        settingsRepository.update(newSettings)
        pushState { copy(isModified = false) }
    }
}
