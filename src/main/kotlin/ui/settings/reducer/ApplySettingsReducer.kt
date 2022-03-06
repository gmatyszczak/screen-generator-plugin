package ui.settings.reducer

import data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.Settings
import ui.core.Reducer
import ui.settings.SettingsAction.ApplySettings
import ui.settings.SettingsState
import javax.inject.Inject

class ApplySettingsReducer @Inject constructor(
    private val state: MutableStateFlow<SettingsState>,
    private val settingsRepository: SettingsRepository
) : Reducer.Blocking<ApplySettings> {

    override fun invoke(action: ApplySettings) {
        val newSettings = state.value.run {
            Settings(
                screenElements = categories.flatMap { it.screenElements }.toMutableList(),
                categories = categories.map { it.category }.toMutableList()
            )
        }
        settingsRepository.update(newSettings)
        state.update { it.copy(isModified = false) }
    }
}
