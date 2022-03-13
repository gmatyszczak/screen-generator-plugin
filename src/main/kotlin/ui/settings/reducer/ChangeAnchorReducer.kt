package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.Anchor
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.ChangeAnchor
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState
import javax.inject.Inject

class ChangeAnchorReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<ChangeAnchor> {

    override suspend fun invoke(action: ChangeAnchor) {
        state.value.selectedElement?.let { screenElement ->
            val newAnchor = Anchor.values()[action.index]
            actionFlow.emit(UpdateScreenElement(screenElement.copy(anchor = newAnchor)))
        }
    }
}
