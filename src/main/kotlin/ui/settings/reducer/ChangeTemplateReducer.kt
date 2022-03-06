package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.ChangeTemplate
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState
import javax.inject.Inject

class ChangeTemplateReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<ChangeTemplate> {

    override suspend fun invoke(action: ChangeTemplate) {
        state.value.selectedElement?.let { screenElement ->
            actionFlow.emit(UpdateScreenElement(screenElement.copy(template = action.text)))
        }
    }
}
