package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.AnchorPosition
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.ChangeAnchorPosition
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState
import javax.inject.Inject

class ChangeAnchorPositionReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<ChangeAnchorPosition> {

    override suspend fun invoke(action: ChangeAnchorPosition) {
        state.value.selectedElement?.let { screenElement ->
            val newAnchorPosition = AnchorPosition.values()[action.index]
            actionFlow.emit(UpdateScreenElement(screenElement.copy(anchorPosition = newAnchorPosition)))
        }
    }
}
