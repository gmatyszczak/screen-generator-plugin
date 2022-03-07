package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CategoryScreenElements
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.MoveDownScreenElement
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import util.swap
import javax.inject.Inject

class MoveDownScreenElementReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val effect: MutableSharedFlow<SettingsEffect>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<MoveDownScreenElement> {

    override suspend fun invoke(action: MoveDownScreenElement) {
        val categoryScreenElements = state.value.selectedCategoryScreenElements
        if (categoryScreenElements != null) {
            val newScreenElements =
                categoryScreenElements.screenElements.toMutableList().apply { swap(action.index, action.index + 1) }
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
            effect.emit(SettingsEffect.SelectScreenElement(action.index + 1))
            actionFlow.emit(SettingsAction.SelectScreenElement(action.index + 1))
        }
    }
}
