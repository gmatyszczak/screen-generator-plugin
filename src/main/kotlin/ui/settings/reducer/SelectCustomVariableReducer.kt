package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface SelectCustomVariableReducer {
    operator fun invoke(index: Int)
}

class SelectCustomVariableReducerImpl @Inject constructor(
    state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope
) : BaseReducer(state, effect, scope), SelectCustomVariableReducer {

    override fun invoke(index: Int) = pushState {
        val customVariables = selectedCategoryIndex?.let { categories[it].category.customVariables } ?: emptyList()
        val selectedIndex =
            if (customVariables.isNotEmpty() && index in customVariables.indices) {
                index
            } else {
                null
            }
        copy(selectedCustomVariableIndex = selectedIndex)
    }
}