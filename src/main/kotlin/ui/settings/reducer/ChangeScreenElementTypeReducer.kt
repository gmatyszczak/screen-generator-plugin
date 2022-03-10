package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.ScreenElementType
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.ChangeScreenElementType
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState
import javax.inject.Inject

class ChangeScreenElementTypeReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<ChangeScreenElementType> {

    override suspend fun invoke(action: ChangeScreenElementType) {
        state.value.selectedElement?.let { screenElement ->
            val newType = ScreenElementType.values()[action.index]
            actionFlow.emit(UpdateScreenElement(screenElement.copy(type = newType)))
        }
    }
}
