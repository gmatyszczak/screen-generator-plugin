package ui.newscreen.reducer

import data.file.CurrentPath
import data.file.PackageExtractor
import data.repository.ModuleRepository
import data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.core.Reducer
import ui.newscreen.NewScreenAction.Init
import ui.newscreen.NewScreenState
import javax.inject.Inject

class InitReducer @Inject constructor(
    private val state: MutableStateFlow<NewScreenState>,
    private val packageExtractor: PackageExtractor,
    private val moduleRepository: ModuleRepository,
    private val currentPath: CurrentPath?,
    private val settingsRepository: SettingsRepository,
) : Reducer.Blocking<Init> {

    override fun invoke(action: Init) =
        state.update {
            it.copy(
                packageName = packageExtractor.extractFromCurrentPath(),
                modules = moduleRepository.getAllModules(),
                selectedModule = currentPath?.module,
                categories = settingsRepository.loadCategories(),
                selectedCategory = settingsRepository.loadCategories().first()
            )
        }
}
