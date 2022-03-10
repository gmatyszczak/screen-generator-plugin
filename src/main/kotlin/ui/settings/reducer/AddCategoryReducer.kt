package ui.settings.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.Category
import model.CategoryScreenElements
import ui.core.Reducer
import ui.settings.SettingsAction.AddCategory
import ui.settings.SettingsState
import javax.inject.Inject

class AddCategoryReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
) : Reducer.Blocking<AddCategory> {

    override fun invoke(action: AddCategory) {
        val newId = state.value.categories.size
        val newCategories = state.value.categories.toMutableList()
            .apply { add(CategoryScreenElements(Category.getDefault(newId), emptyList())) }
        state.update {
            it.copy(
                isModified = true,
                categories = newCategories,
                selectedCategoryIndex = newCategories.lastIndex,
                selectedElementIndex = null,
                selectedCustomVariableIndex = null,
            )
        }
    }
}
