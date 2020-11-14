package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import ui.settings.renderSampleCode
import ui.settings.renderSampleFileName

interface ChangeFragmentReducer {

    operator fun invoke(text: String)
}

class ChangeFragmentReducerImpl(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope
) : BaseReducer(state, effect, scope), ChangeFragmentReducer {

    override fun invoke(text: String) = pushState {
        copy(
            fragmentBaseClass = text,
            sampleCode = selectedElement?.renderSampleCode(activityBaseClass) ?: "",
            fileNameRendered = selectedElement?.renderSampleFileName(activityBaseClass) ?: "",
            isModified = true
        )
    }
}
