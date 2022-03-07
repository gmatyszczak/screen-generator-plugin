package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.AndroidComponent
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.ChangeAndroidComponent
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState
import javax.inject.Inject

class ChangeAndroidComponentReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<ChangeAndroidComponent> {

    override suspend fun invoke(action: ChangeAndroidComponent) {
        state.value.selectedElement?.let { screenElement ->
            val newAndroidComponent = AndroidComponent.values()[action.index]
            actionFlow.emit(UpdateScreenElement(screenElement.copy(relatedAndroidComponent = newAndroidComponent)))
        }
    }
}
