package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.CategoryScreenElements
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import util.swap
import javax.inject.Inject

interface MoveUpScreenElementReducer {
    operator fun invoke(index: Int)
}

class MoveUpScreenElementReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectScreenElementReducer: SelectScreenElementReducer
) : BaseReducer(state, effect, scope), MoveUpScreenElementReducer {

    override fun invoke(index: Int) {
        val categoryScreenElements = state.value.selectedCategoryScreenElements
        if (categoryScreenElements != null) {
            val newScreenElements = categoryScreenElements.screenElements.toMutableList().apply { swap(index, index - 1) }
            val newCategories =
                state.value.categories
                    .toMutableList()
                    .apply {
                        set(
                            state.value.selectedCategoryIndex!!,
                            CategoryScreenElements(categoryScreenElements.category, newScreenElements)
                        )
                    }
            pushState {
                copy(
                    isModified = true,
                    categories = newCategories
                )
            }
            pushEffect(SettingsEffect.SelectScreenElement(index - 1))
            selectScreenElementReducer(index - 1)
        }
    }
}
