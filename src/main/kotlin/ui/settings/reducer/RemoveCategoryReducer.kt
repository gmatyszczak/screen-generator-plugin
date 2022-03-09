package ui.settings.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.settings.SettingsAction.RemoveCategory
import ui.settings.SettingsState
import javax.inject.Inject

class RemoveCategoryReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
) : Reducer.Blocking<RemoveCategory> {

    override fun invoke(action: RemoveCategory) {
        val newCategories =
            state.value.categories
                .toMutableList()
                .apply { removeAt(action.index) }
        state.update {
            it.copy(
                isModified = true,
                categories = newCategories,
                selectedCategoryIndex = null,
                selectedElementIndex = null,
                selectedCustomVariableIndex = null,
            )
        }
    }
}
