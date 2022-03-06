package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.FileType
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.ChangeFileType
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState
import javax.inject.Inject

class ChangeFileTypeReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<ChangeFileType> {

    override suspend fun invoke(action: ChangeFileType) {
        state.value.selectedElement?.let { screenElement ->
            val newFileType = FileType.values()[action.index]
            actionFlow.emit(
                UpdateScreenElement(
                    screenElement.copy(
                        fileType = newFileType,
                        fileNameTemplate = newFileType.defaultFileName,
                        template = newFileType.defaultTemplate
                    )
                )
            )
        }
    }
}
