package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState

interface ChangeNameReducer {

    operator fun invoke(text: String)
}

class ChangeNameReducerImpl(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val updateScreenElementReducer: UpdateScreenElementReducer
) : BaseReducer(state, effect, scope), ChangeNameReducer {

    override fun invoke(text: String) {
        state.value.selectedElement?.let {
            updateScreenElementReducer(it.copy(name = text))
        }
    }
}
