package ui.settings.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.settings.SettingsAction.MoveUpCategory
import ui.settings.SettingsState
import util.swap
import javax.inject.Inject

class MoveUpCategoryReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
) : Reducer.Blocking<MoveUpCategory> {

    override fun invoke(action: MoveUpCategory) {
        if (action.index > 0) {
            val newCategories =
                state.value.categories
                    .toMutableList()
                    .apply { swap(action.index, action.index - 1) }
            state.update {
                it.copy(
                    isModified = true,
                    categories = newCategories,
                    selectedCategoryIndex = action.index - 1,
                )
            }
        }
    }
}
