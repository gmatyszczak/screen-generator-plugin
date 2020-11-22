package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface SelectCategoryReducer {
    operator fun invoke(index: Int)
}

class SelectCategoryReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectScreenElementReducer: SelectScreenElementReducer
) : BaseReducer(state, effect, scope), SelectCategoryReducer {

    override fun invoke(index: Int) {
        val selectedIndex =
            if (state.value.categories.isNotEmpty() && index in state.value.categories.indices) {
                index
            } else {
                null
            }
        val selectedCategory = selectedIndex?.let { state.value.categories[selectedIndex] }
        val selectedElement = selectedCategory?.screenElements?.firstOrNull()
        pushState { copy(selectedCategoryIndex = selectedIndex) }
        if (selectedElement != null) {
            selectScreenElementReducer(0)
            pushEffect(SettingsEffect.SelectScreenElement(0))
        } else {
            selectScreenElementReducer(-1)
        }
    }
}