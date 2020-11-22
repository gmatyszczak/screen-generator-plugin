package ui.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.CategoryScreenElements
import model.ScreenElement
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface AddScreenElementReducer {
    operator fun invoke()
}

class AddScreenElementReducerImpl @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val selectScreenElementReducer: SelectScreenElementReducer
) : BaseReducer(state, effect, scope), AddScreenElementReducer {

    override fun invoke() {
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
            pushState {
                copy(
                    isModified = true,
                    categories = newCategories
                )
            }
            pushEffect(SettingsEffect.SelectScreenElement(newList.size - 1))
            selectScreenElementReducer(newList.size - 1)
        }
    }
}
