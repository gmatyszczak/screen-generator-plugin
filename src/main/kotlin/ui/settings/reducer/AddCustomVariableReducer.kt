package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CategoryScreenElements
import model.CustomVariable
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.AddCustomVariable
import ui.settings.SettingsState
import javax.inject.Inject

class AddCustomVariableReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<AddCustomVariable> {

    override suspend fun invoke(action: AddCustomVariable) {
        state.value.selectedCategoryScreenElements?.let { selectedCategoryScreenElements ->
            val newVariables = selectedCategoryScreenElements.category.customVariables.toMutableList().apply {
                add(CustomVariable.getDefault())
            }
            val newCategories = state.value.categories.toMutableList()
                .apply {
                    set(
                        state.value.selectedCategoryIndex!!,
                        CategoryScreenElements(
                            selectedCategoryScreenElements.category.copy(customVariables = newVariables),
                            selectedCategoryScreenElements.screenElements
                        )
                    )
                }
            state.update {
                it.copy(
                    isModified = true,
                    categories = newCategories
                )
            }
            actionFlow.emit(SettingsAction.SelectCustomVariable(newVariables.size - 1))
        }
    }
}
