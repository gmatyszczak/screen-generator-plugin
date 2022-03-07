package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.ChangeCategoryName
import ui.settings.SettingsState
import javax.inject.Inject

class ChangeCategoryNameReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<ChangeCategoryName> {

    override suspend fun invoke(action: ChangeCategoryName) {
        state.value.selectedCategoryScreenElements?.let {
            actionFlow.emit(SettingsAction.UpdateCategory(it.category.copy(name = action.text)))
        }
    }
}
