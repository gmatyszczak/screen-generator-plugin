package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CategoryScreenElements
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.MoveUpScreenElement
import ui.settings.SettingsState
import util.swap
import javax.inject.Inject

class MoveUpScreenElementReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<MoveUpScreenElement> {

    override suspend fun invoke(action: MoveUpScreenElement) {
        val categoryScreenElements = state.value.selectedCategoryScreenElements
        if (categoryScreenElements != null) {
            val newScreenElements =
                categoryScreenElements.screenElements.toMutableList().apply { swap(action.index, action.index - 1) }
            val newCategories =
                state.value.categories
                    .toMutableList()
                    .apply {
                        set(
                            state.value.selectedCategoryIndex!!,
                            CategoryScreenElements(categoryScreenElements.category, newScreenElements)
                        )
                    }
            state.update {
                it.copy(
                    isModified = true,
                    categories = newCategories
                )
            }
            actionFlow.emit(SettingsAction.SelectScreenElement(action.index - 1))
        }
    }
}
