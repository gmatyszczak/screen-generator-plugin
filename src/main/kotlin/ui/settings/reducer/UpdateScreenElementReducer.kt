package ui.settings.reducer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CategoryScreenElements
import ui.core.Reducer
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsState
import ui.settings.renderSampleCode
import ui.settings.renderSampleFileName
import javax.inject.Inject

class UpdateScreenElementReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
) : Reducer.Blocking<UpdateScreenElement> {

    override fun invoke(action: UpdateScreenElement) {
        state.update { state ->
            val categoryScreenElements = state.categories[state.selectedCategoryIndex!!]
            val newScreenElements = categoryScreenElements.screenElements.toMutableList()
                .apply { set(state.selectedElementIndex!!, action.element) }
            val newCategories = state.categories.toMutableList()
                .apply {
                    set(
                        state.selectedCategoryIndex,
                        CategoryScreenElements(categoryScreenElements.category, newScreenElements)
                    )
                }
            state.copy(
                categories = newCategories,
                fileNameRendered = action.element.renderSampleFileName(),
                sampleCode = action.element.renderSampleCode(),
                isModified = true
            )
        }
    }
}
