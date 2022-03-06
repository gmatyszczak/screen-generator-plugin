package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.SelectCategory
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

class SelectCategoryReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val effect: MutableSharedFlow<SettingsEffect>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<SelectCategory> {

    override suspend fun invoke(action: SelectCategory) {
        val selectedIndex =
            if (state.value.categories.isNotEmpty() && action.index in state.value.categories.indices) {
                action.index
            } else {
                null
            }
        val selectedCategory = selectedIndex?.let { state.value.categories[selectedIndex] }
        val selectedElement = selectedCategory?.screenElements?.firstOrNull()
        state.update {
            it.copy(
                selectedCategoryIndex = selectedIndex
            )
        }
        actionFlow.emit(SettingsAction.SelectCustomVariable(-1))
        if (selectedElement != null) {
            actionFlow.emit(SettingsAction.SelectScreenElement(0))
            effect.emit(SettingsEffect.SelectScreenElement(0))
        } else {
            actionFlow.emit(SettingsAction.SelectScreenElement(-1))
        }
    }
}
