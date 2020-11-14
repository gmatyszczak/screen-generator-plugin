package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.ScreenElement
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import ui.settings.renderSampleCode
import ui.settings.renderSampleFileName

interface UpdateScreenElementReducer {

    operator fun invoke(updatedElement: ScreenElement)
}

class UpdateScreenElementReducerImpl(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
) : BaseReducer(state, effect, scope), UpdateScreenElementReducer {

    override fun invoke(updatedElement: ScreenElement) {
        pushState {
            val newScreenElements = state.value.screenElements.toMutableList()
                .apply { set(selectedElementIndex!!, updatedElement) }
            copy(
                screenElements = newScreenElements,
                fileNameRendered = updatedElement.renderSampleFileName(activityBaseClass),
                sampleCode = updatedElement.renderSampleCode(activityBaseClass),
                isModified = true
            )
        }
    }
}
