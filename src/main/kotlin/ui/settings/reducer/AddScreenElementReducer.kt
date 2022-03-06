package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CategoryScreenElements
import model.ScreenElement
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.AddScreenElement
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

class AddScreenElementReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val effect: MutableSharedFlow<SettingsEffect>,
    private val actionFlow: MutableSharedFlow<SettingsAction>,
) : Reducer.Suspend<AddScreenElement> {

    override suspend fun invoke(action: AddScreenElement) {
        val categoryScreenElements = state.value.selectedCategoryScreenElements
        if (categoryScreenElements != null) {
            val newList =
                categoryScreenElements.screenElements.toMutableList()
                    .apply { add(ScreenElement.getDefault(state.value.selectedCategoryScreenElements!!.category.id)) }
            val newCategories = state.value.categories.toMutableList().apply {
                set(
                    state.value.selectedCategoryIndex!!,
                    CategoryScreenElements(categoryScreenElements.category, newList)
                )
            }
            state.update {
                it.copy(
                    isModified = true,
                    categories = newCategories
                )
            }
            effect.emit(SettingsEffect.SelectScreenElement(newList.size - 1))
            actionFlow.emit(SettingsAction.SelectScreenElement(newList.size - 1))
        }
    }
}
