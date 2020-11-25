package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.CategoryScreenElements
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface RemoveCustomVariableReducer {
    operator fun invoke(index: Int)
}

class RemoveCustomVariableReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectCustomVariableReducer: SelectCustomVariableReducer
) : BaseReducer(state, effect, scope), RemoveCustomVariableReducer {

    override fun invoke(index: Int) {
        val categoryScreenElements = state.value.selectedCategoryScreenElements
        if (categoryScreenElements != null) {
            val newCustomVariables =
                categoryScreenElements.category.customVariables.toMutableList().apply { removeAt(index) }
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
            pushState {
                copy(
                    isModified = true,
                    categories = newCategories
                )
            }
            selectCustomVariableReducer(index)
        }
    }
}
