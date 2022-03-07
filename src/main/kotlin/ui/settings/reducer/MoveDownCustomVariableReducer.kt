package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CategoryScreenElements
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.MoveDownCustomVariable
import ui.settings.SettingsState
import util.swap
import javax.inject.Inject

class MoveDownCustomVariableReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<MoveDownCustomVariable> {

    override suspend fun invoke(action: MoveDownCustomVariable) {
        state.value.selectedCategoryScreenElements?.let { selectedCategoryScreenElements ->
            val newVariables = selectedCategoryScreenElements.category.customVariables.toMutableList().apply {
                swap(action.index, action.index + 1)
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
            actionFlow.emit(SettingsAction.SelectCustomVariable(action.index + 1))
        }
    }
}
