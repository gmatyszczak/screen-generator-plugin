package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import ui.settings.renderSampleCode
import ui.settings.renderSampleFileName
import javax.inject.Inject

interface ChangeActivityReducer {

    operator fun invoke(text: String)
}

class ChangeActivityReducerImpl @Inject constructor(
    state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope
) : BaseReducer(state, effect, scope), ChangeActivityReducer {

    override fun invoke(text: String) = pushState {
        copy(
            activityBaseClass = text,
            sampleCode = selectedElement?.renderSampleCode(text) ?: "",
            fileNameRendered = selectedElement?.renderSampleFileName(text) ?: "",
            isModified = true
        )
    }
}
