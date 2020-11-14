package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.FileType
import ui.settings.SettingsEffect
import ui.settings.SettingsState

interface ChangeFileTypeReducer {

    operator fun invoke(index: Int)
}

class ChangeFileTypeReducerImpl(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val updateScreenElementReducer: UpdateScreenElementReducer
) : BaseReducer(state, effect, scope), ChangeFileTypeReducer {

    override fun invoke(index: Int) {
        state.value.selectedElement?.let {
            val newFileType = FileType.values()[index]
            updateScreenElementReducer(
                it.copy(
                    fileType = newFileType,
                    fileNameTemplate = newFileType.defaultFileName,
                    template = newFileType.defaultTemplate
                )
            )
        }
    }
}
