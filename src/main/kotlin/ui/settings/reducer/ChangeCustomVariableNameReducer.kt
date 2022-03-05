package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.CategoryScreenElements
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface ChangeCustomVariableNameReducer {

    operator fun invoke(text: String)
}

class ChangeCustomVariableNameReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
) : BaseReducer(state, effect, scope), ChangeCustomVariableNameReducer {

    override fun invoke(text: String) {
        state.value.selectedCustomVariable?.let { customVariable ->
            pushState {
                val categoryScreenElements = categories[selectedCategoryIndex!!]
                val updatedCustomVariables = categoryScreenElements.category.customVariables.toMutableList().apply {
                    set(selectedCustomVariableIndex!!, customVariable.copy(name = text))
                }
                val updatedCategory = categoryScreenElements.category.copy(customVariables = updatedCustomVariables)
                val newCategories = categories.toMutableList()
                    .apply {
                        set(
                            selectedCategoryIndex,
                            CategoryScreenElements(updatedCategory, categoryScreenElements.screenElements)
                        )
                    }
                copy(
                    categories = newCategories,
                    isModified = true
                )
            }
        }
    }
}
