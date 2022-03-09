package ui.settings.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.settings.SettingsAction.SelectCategory
import ui.settings.SettingsState
import javax.inject.Inject

class SelectCategoryReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
) : Reducer.Blocking<SelectCategory> {

    override fun invoke(action: SelectCategory) {
        val selectedIndex =
            if (state.value.categories.isNotEmpty() && action.index in state.value.categories.indices) {
                action.index
            } else {
                null
            }
        val selectedCategory = selectedIndex?.let { state.value.categories[selectedIndex] }
        val selectedElementIndex =
            selectedCategory
                ?.screenElements
                ?.let { if (it.isNotEmpty()) 0 else null }
        val selectedCustomVariableIndex =
            selectedCategory
                ?.category
                ?.customVariables
                ?.let { if (it.isNotEmpty()) 0 else null }
        state.update {
            it.copy(
                selectedCategoryIndex = selectedIndex,
                selectedElementIndex = selectedElementIndex,
                selectedCustomVariableIndex = selectedCustomVariableIndex,
            )
        }
    }
}
