package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState

interface RemoveScreenElementReducer {
    operator fun invoke(index: Int)
}

class RemoveScreenElementReducerImpl(
    state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectScreenElementReducer: SelectScreenElementReducer
) : BaseReducer(state, effect, scope), RemoveScreenElementReducer {

    override fun invoke(index: Int) {
        pushState {
            copy(
                isModified = true,
                screenElements = screenElements.toMutableList().apply { removeAt(index) })
        }
        selectScreenElementReducer(index)
    }
}
