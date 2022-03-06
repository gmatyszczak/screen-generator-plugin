package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.ChangeFileName
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState
import javax.inject.Inject

class ChangeFileNameReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<ChangeFileName> {

    override suspend fun invoke(action: ChangeFileName) {
        state.value.selectedElement?.let { screenElement ->
            actionFlow.emit(UpdateScreenElement(screenElement.copy(fileNameTemplate = action.text)))
        }
    }
}
