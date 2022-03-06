package ui.settings.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.settings.SettingsAction.SelectCustomVariable
import ui.settings.SettingsState
import javax.inject.Inject

class SelectCustomVariableReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
) : Reducer.Blocking<SelectCustomVariable> {

    override fun invoke(action: SelectCustomVariable) {
        state.update { state ->
            val customVariables =
                state.selectedCategoryIndex?.let { state.categories[it].category.customVariables } ?: emptyList()
            val selectedIndex =
                if (customVariables.isNotEmpty() && action.index in customVariables.indices) {
                    action.index
                } else {
                    null
                }
            state.copy(selectedCustomVariableIndex = selectedIndex)
        }
    }
}
