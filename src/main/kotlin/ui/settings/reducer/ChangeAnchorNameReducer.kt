package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.ChangeAnchorName
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState
import javax.inject.Inject

class ChangeAnchorNameReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<ChangeAnchorName> {

    override suspend fun invoke(action: ChangeAnchorName) {
        state.value.selectedElement?.let { screenElement ->
            actionFlow.emit(UpdateScreenElement(screenElement.copy(anchorName = action.name)))
        }
    }
}
