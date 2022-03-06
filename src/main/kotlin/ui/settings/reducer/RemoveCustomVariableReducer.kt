package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CategoryScreenElements
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.RemoveCustomVariable
import ui.settings.SettingsAction.SelectCustomVariable
import ui.settings.SettingsState
import javax.inject.Inject

class RemoveCustomVariableReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<RemoveCustomVariable> {

    override suspend fun invoke(action: RemoveCustomVariable) {
        val categoryScreenElements = state.value.selectedCategoryScreenElements
        if (categoryScreenElements != null) {
            val newCustomVariables =
                categoryScreenElements.category.customVariables.toMutableList().apply { removeAt(action.index) }
            val newCategory = categoryScreenElements.category.copy(customVariables = newCustomVariables)
            val newCategories =
                state.value.categories
                    .toMutableList()
                    .apply {
                        set(
                            state.value.selectedCategoryIndex!!,
                            CategoryScreenElements(newCategory, categoryScreenElements.screenElements)
                        )
                    }
            state.update {
                it.copy(
                    isModified = true,
                    categories = newCategories
                )
            }
            actionFlow.emit(SelectCustomVariable(action.index))
        }
    }
}
