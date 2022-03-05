package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.Category
import model.CategoryScreenElements
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface UpdateCategoryReducer {

    operator fun invoke(updatedCategory: Category)
}

class UpdateCategoryReducerImpl @Inject constructor(
    state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
) : BaseReducer(state, effect, scope), UpdateCategoryReducer {

    override fun invoke(updatedCategory: Category) {
        pushState {
            val categoryScreenElements = categories[selectedCategoryIndex!!]
            val newCategories = categories.toMutableList()
                .apply {
                    set(
                        selectedCategoryIndex,
                        CategoryScreenElements(updatedCategory, categoryScreenElements.screenElements)
                    )
                }
            copy(
                categories = newCategories,
                isModified = true
            )
        }
    }
}
