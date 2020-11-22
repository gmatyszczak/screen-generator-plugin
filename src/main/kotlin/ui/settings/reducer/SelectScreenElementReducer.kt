package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import ui.settings.renderSampleCode
import ui.settings.renderSampleFileName
import javax.inject.Inject

interface SelectScreenElementReducer {
    operator fun invoke(index: Int)
}

class SelectScreenElementReducerImpl @Inject constructor(
    state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope
) : BaseReducer(state, effect, scope), SelectScreenElementReducer {

    override fun invoke(index: Int) = pushState {
        val screenElements = selectedCategoryIndex?.let { categories[it].screenElements } ?: emptyList()
        val selectedIndex =
            if (screenElements.isNotEmpty() && index in screenElements.indices) {
                index
            } else {
                null
            }
        val selectedElement = selectedIndex?.let { screenElements[selectedIndex] }
        val fileName = selectedElement?.renderSampleFileName() ?: ""
        val sampleCode = selectedElement?.renderSampleCode() ?: ""
        copy(
            selectedElementIndex = selectedIndex,
            fileNameRendered = fileName,
            sampleCode = sampleCode
        )
    }
}
