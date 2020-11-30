package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.CategoryScreenElements
import model.CustomVariable
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface AddCustomVariableReducer {
    operator fun invoke()
}

class AddCustomVariableReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectCustomVariableReducer: SelectCustomVariableReducer
) : BaseReducer(state, effect, scope), AddCustomVariableReducer {

    override fun invoke() {
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
            pushState {
                copy(
                    isModified = true,
                    categories = newCategories
                )
            }
            selectCustomVariableReducer(newVariables.size - 1)
        }
    }
}
