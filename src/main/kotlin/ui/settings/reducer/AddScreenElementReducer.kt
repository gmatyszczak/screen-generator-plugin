package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.ScreenElement
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface AddScreenElementReducer {
    operator fun invoke()
}

class AddScreenElementReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectScreenElementReducer: SelectScreenElementReducer
) : BaseReducer(state, effect, scope), AddScreenElementReducer {

    override fun invoke() {
        val newList = state.value.screenElements.toMutableList().apply { add(ScreenElement.getDefault()) }
        pushState {
            copy(
                isModified = true,
                screenElements = newList
            )
        }
        pushEffect(SettingsEffect.SelectScreenElement(newList.size - 1))
        selectScreenElementReducer(newList.size - 1)
    }
}
