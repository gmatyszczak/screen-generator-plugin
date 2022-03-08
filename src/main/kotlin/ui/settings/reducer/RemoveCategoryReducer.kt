package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.RemoveCategory
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

class RemoveCategoryReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val effect: MutableSharedFlow<SettingsEffect>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<RemoveCategory> {

    override suspend fun invoke(action: RemoveCategory) {
        val newCategories =
            state.value.categories
                .toMutableList()
                .apply { removeAt(state.value.selectedCategoryIndex!!) }
        state.update {
            it.copy(
                isModified = true,
                categories = newCategories,
                selectedCategoryIndex = null,
                selectedElementIndex = null,
            )
        }
        effect.emit(SettingsEffect.SelectScreenElement(-1))
        actionFlow.emit(SettingsAction.SelectCategory(action.index))
    }
}
