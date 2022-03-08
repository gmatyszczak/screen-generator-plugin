package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.MoveUpCategory
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import util.swap
import javax.inject.Inject

class MoveUpCategoryReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val effect: MutableSharedFlow<SettingsEffect>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<MoveUpCategory> {

    override suspend fun invoke(action: MoveUpCategory) {
        val newCategories =
            state.value.categories
                .toMutableList()
                .apply { swap(action.index, action.index - 1) }
        state.update {
            it.copy(
                isModified = true,
                categories = newCategories,
                selectedCategoryIndex = action.index - 1,
                selectedElementIndex = null,
            )
        }
        effect.emit(SettingsEffect.SelectScreenElement(-1))
        actionFlow.emit(SettingsAction.SelectCategory(action.index - 1))
    }
}
