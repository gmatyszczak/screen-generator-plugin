package ui.newscreen.reducer

import data.file.CurrentPath
import data.file.PackageExtractor
import data.repository.ModuleRepository
import data.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.newscreen.NewScreenEffect
import ui.newscreen.NewScreenState
import javax.inject.Inject

interface InitReducer {

    operator fun invoke()
}

class InitReducerImpl @Inject constructor(
    private val state: MutableStateFlow<NewScreenState>,
    effect: MutableSharedFlow<NewScreenEffect>,
    scope: CoroutineScope,
    private val packageExtractor: PackageExtractor,
    private val moduleRepository: ModuleRepository,
    private val currentPath: CurrentPath?,
    private val settingsRepository: SettingsRepository,
) : BaseReducer(state, effect, scope), InitReducer {

    override fun invoke() = pushState {
        copy(
            packageName = packageExtractor.extractFromCurrentPath(),
            modules = moduleRepository.getAllModules(),
            selectedModule = currentPath?.module,
            categories = settingsRepository.loadCategories(),
            selectedCategory = settingsRepository.loadCategories().first()
        )
    }
}
