package ui.settings.reducer

import data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.settings.SettingsAction.ResetSettings
import ui.settings.SettingsState
import javax.inject.Inject

class ResetSettingsReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val settingsRepository: SettingsRepository
) : Reducer.Blocking<ResetSettings> {

    override fun invoke(action: ResetSettings) {
        val categoriesWithScreenElements = settingsRepository.loadCategoriesWithScreenElements()
        state.update {
            val selectedCategory = if (categoriesWithScreenElements.isNotEmpty()) 0 else null
            it.copy(
                selectedElementIndex = null,
                fileNameRendered = "",
                sampleCode = "",
                isModified = false,
                categories = categoriesWithScreenElements,
                selectedCategoryIndex = selectedCategory
            )
        }
    }
}
