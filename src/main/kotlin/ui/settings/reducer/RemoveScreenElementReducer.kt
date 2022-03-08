package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CategoryScreenElements
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.RemoveScreenElement
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

class RemoveScreenElementReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val effect: MutableSharedFlow<SettingsEffect>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<RemoveScreenElement> {

    override suspend fun invoke(action: RemoveScreenElement) {
        val categoryScreenElements = state.value.selectedCategoryScreenElements
        if (categoryScreenElements != null) {
            val newScreenElements =
                categoryScreenElements.screenElements.toMutableList().apply { removeAt(action.index) }
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
                    categories = newCategories,
                    selectedElementIndex = null,
                )
            }
            effect.emit(SettingsEffect.SelectScreenElement(-1))
            actionFlow.emit(SettingsAction.SelectScreenElement(action.index))
        }
    }
}
