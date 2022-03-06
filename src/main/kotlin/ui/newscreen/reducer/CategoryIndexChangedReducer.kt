package ui.newscreen.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.newscreen.NewScreenAction.CategoryIndexChanged
import ui.newscreen.NewScreenState
import javax.inject.Inject

class CategoryIndexChangedReducer @Inject constructor(
    private val state: MutableStateFlow<NewScreenState>,
) : Reducer.Blocking<CategoryIndexChanged> {

    override fun invoke(action: CategoryIndexChanged) {
        state.update { it.copy(selectedCategory = it.categories[action.index]) }
    }
}
