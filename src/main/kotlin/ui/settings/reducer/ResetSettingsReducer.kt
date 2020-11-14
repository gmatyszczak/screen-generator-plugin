package ui.settings.reducer

import data.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState

interface ResetSettingsReducer {

    operator fun invoke()
}

class ResetSettingsReducerImpl(
    state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val settingsRepository: SettingsRepository
) : BaseReducer(state, effect, scope), ResetSettingsReducer {

    override fun invoke() {
        val settings = settingsRepository.loadSettings()
        pushState {
            copy(
                screenElements = settings.screenElements,
                activityBaseClass = settings.activityBaseClass,
                fragmentBaseClass = settings.fragmentBaseClass,
                selectedElementIndex = null,
                fileNameRendered = "",
                sampleCode = "",
                isModified = false
            )
        }
    }
}
