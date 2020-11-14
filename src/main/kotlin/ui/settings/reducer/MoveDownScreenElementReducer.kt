package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import util.swap

interface MoveDownScreenElementReducer {
    operator fun invoke(index: Int)
}

class MoveDownScreenElementReducerImpl(
    state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectScreenElementReducer: SelectScreenElementReducer
) : BaseReducer(state, effect, scope), MoveDownScreenElementReducer {

    override fun invoke(index: Int) {
        pushState {
            copy(
                isModified = true,
                screenElements = screenElements.toMutableList().apply { swap(index, index + 1) })
        }
        pushEffect(SettingsEffect.SelectScreenElement(index + 1))
        selectScreenElementReducer(index + 1)
    }
}
