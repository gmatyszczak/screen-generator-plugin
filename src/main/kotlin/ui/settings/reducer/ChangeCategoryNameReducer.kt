package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface ChangeCategoryNameReducer {

    operator fun invoke(text: String)
}

class ChangeCategoryNameReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val updateCategoryReducer: UpdateCategoryReducer
) : BaseReducer(state, effect, scope), ChangeCategoryNameReducer {

    override fun invoke(text: String) {
        state.value.selectedCategoryScreenElements?.let {
            updateCategoryReducer(it.category.copy(name = text))
        }
    }
}
