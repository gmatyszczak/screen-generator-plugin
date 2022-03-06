package ui.settings.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.settings.SettingsAction.SelectScreenElement
import ui.settings.SettingsState
import ui.settings.renderSampleCode
import ui.settings.renderSampleFileName
import javax.inject.Inject

class SelectScreenElementReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
) : Reducer.Blocking<SelectScreenElement> {

    override fun invoke(action: SelectScreenElement) {
        state.update { state ->
            val screenElements = state.selectedCategoryIndex?.let { state.categories[it].screenElements } ?: emptyList()
            val selectedIndex =
                if (screenElements.isNotEmpty() && action.index in screenElements.indices) {
                    action.index
                } else {
                    null
                }
            val selectedElement = selectedIndex?.let { screenElements[selectedIndex] }
            val fileName = selectedElement?.renderSampleFileName() ?: ""
            val sampleCode = selectedElement?.renderSampleCode() ?: ""
            state.copy(
                selectedElementIndex = selectedIndex,
                fileNameRendered = fileName,
                sampleCode = sampleCode
            )
        }
    }
}
