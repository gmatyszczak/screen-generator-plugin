package ui.settings.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CategoryScreenElements
import ui.core.Reducer
import ui.settings.SettingsAction.ChangeCustomVariableName
import ui.settings.SettingsState
import javax.inject.Inject

class ChangeCustomVariableNameReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
) : Reducer.Blocking<ChangeCustomVariableName> {

    override fun invoke(action: ChangeCustomVariableName) {
        state.value.selectedCustomVariable?.let { customVariable ->
            state.update { state ->
                val categoryScreenElements = state.categories[state.selectedCategoryIndex!!]
                val updatedCustomVariables = categoryScreenElements.category.customVariables.toMutableList().apply {
                    set(state.selectedCustomVariableIndex!!, customVariable.copy(name = action.text))
                }
                val updatedCategory = categoryScreenElements.category.copy(customVariables = updatedCustomVariables)
                val newCategories = state.categories.toMutableList()
                    .apply {
                        set(
                            state.selectedCategoryIndex,
                            CategoryScreenElements(updatedCategory, categoryScreenElements.screenElements)
                        )
                    }
                state.copy(
                    categories = newCategories,
                    isModified = true
                )
            }
        }
    }
}
