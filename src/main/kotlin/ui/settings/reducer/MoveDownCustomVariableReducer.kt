package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.CategoryScreenElements
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import util.swap
import javax.inject.Inject

interface MoveDownCustomVariableReducer {
    operator fun invoke(index: Int)
}

class MoveDownCustomVariableReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectCustomVariableReducer: SelectCustomVariableReducer
) : BaseReducer(state, effect, scope), MoveDownCustomVariableReducer {

    override fun invoke(index: Int) {
        state.value.selectedCategoryScreenElements?.let { selectedCategoryScreenElements ->
            val newVariables = selectedCategoryScreenElements.category.customVariables.toMutableList().apply {
                swap(index, index + 1)
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
            selectCustomVariableReducer(index + 1)
        }
    }
}
