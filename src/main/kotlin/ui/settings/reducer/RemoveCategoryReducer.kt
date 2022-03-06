package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface RemoveCategoryReducer {
    operator fun invoke(index: Int)
}

class RemoveCategoryReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectCategoryReducer: SelectCategoryReducer
) : BaseReducer(state, effect, scope), RemoveCategoryReducer {

    override fun invoke(index: Int) {
        val newCategories =
            state.value.categories
                .toMutableList()
                .apply { removeAt(state.value.selectedCategoryIndex!!) }
        pushState {
            copy(
                isModified = true,
                categories = newCategories
            )
        }
        selectCategoryReducer(index)
    }
}
