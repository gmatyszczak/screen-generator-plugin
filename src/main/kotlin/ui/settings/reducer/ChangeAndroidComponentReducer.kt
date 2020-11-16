package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.AndroidComponent
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface ChangeAndroidComponentReducer {

    operator fun invoke(index: Int)
}

class ChangeAndroidComponentReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val updateScreenElementReducer: UpdateScreenElementReducer
) : BaseReducer(state, effect, scope), ChangeAndroidComponentReducer {

    override fun invoke(index: Int) {
        state.value.selectedElement?.let {
            val newAndroidComponent = AndroidComponent.values()[index]
            updateScreenElementReducer(it.copy(relatedAndroidComponent = newAndroidComponent))
        }
    }
}
