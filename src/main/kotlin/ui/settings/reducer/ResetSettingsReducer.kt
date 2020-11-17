package ui.settings.reducer

import data.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import javax.inject.Inject

interface ResetSettingsReducer {

    operator fun invoke()
}

class ResetSettingsReducerImpl @Inject constructor(
    state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    scope: CoroutineScope,
    private val settingsRepository: SettingsRepository
) : BaseReducer(state, effect, scope), ResetSettingsReducer {

    override fun invoke() {
        val categoriesWithScreenElements = settingsRepository.loadCategoriesWithScreenElements()
        pushState {
            val selectedCategory = if (categoriesWithScreenElements.isNotEmpty()) 0 else null
            copy(
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
