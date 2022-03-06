package ui.settings.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CategoryScreenElements
import ui.core.Reducer
import ui.settings.SettingsAction.UpdateCategory
import ui.settings.SettingsState
import javax.inject.Inject

class UpdateCategoryReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
) : Reducer.Blocking<UpdateCategory> {

    override fun invoke(action: UpdateCategory) {
        state.update {
            val categoryScreenElements = it.categories[it.selectedCategoryIndex!!]
            val newCategories = it.categories.toMutableList()
                .apply {
                    set(
                        it.selectedCategoryIndex,
                        CategoryScreenElements(action.category, categoryScreenElements.screenElements)
                    )
                }
            it.copy(
                categories = newCategories,
                isModified = true
            )
        }
    }
}
