package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface ChangeSourceSetReducer {

    operator fun invoke(text: String)
}

class ChangeSourceSetReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val updateScreenElementReducer: UpdateScreenElementReducer
) : BaseReducer(state, effect, scope), ChangeSourceSetReducer {

    override fun invoke(text: String) {
        state.value.selectedElement?.let {
            updateScreenElementReducer(it.copy(sourceSet = text))
        }
    }
}