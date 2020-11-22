package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import util.swap
import javax.inject.Inject

interface MoveUpCategoryReducer {
    operator fun invoke(index: Int)
}

class MoveUpCategoryReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectCategoryReducer: SelectCategoryReducer
) : BaseReducer(state, effect, scope), MoveUpCategoryReducer {

    override fun invoke(index: Int) {
        val newCategories =
            state.value.categories
                .toMutableList()
                .apply { swap(index, index - 1) }
        pushState {
            copy(
                isModified = true,
                categories = newCategories
            )
        }
        selectCategoryReducer(index - 1)
    }
}
