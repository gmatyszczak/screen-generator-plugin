package ui.newscreen

import data.file.CurrentPath
import data.file.FileCreator
import data.file.PackageExtractor
import data.file.WriteActionDispatcher
import data.repository.ModuleRepository
import data.repository.SettingsRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.AndroidComponent
import model.Category
import model.Module
import javax.inject.Inject

class NewScreenViewModel @Inject constructor(
    private val fileCreator: FileCreator,
    packageExtractor: PackageExtractor,
    private val writeActionDispatcher: WriteActionDispatcher,
    moduleRepository: ModuleRepository,
    currentPath: CurrentPath?,
    settingsRepository: SettingsRepository
) {

    private val scope = MainScope()

    val state = MutableStateFlow(
        NewScreenState(
            packageName = packageExtractor.extractFromCurrentPath(),
            modules = moduleRepository.getAllModules(),
            selectedModule = currentPath?.module,
            categories = settingsRepository.loadCategories(),
            selectedCategory = settingsRepository.loadCategories().first()
        )
    )

    val effect = MutableSharedFlow<NewScreenEffect>(replay = 0)

    fun onOkClick(
        packageName: String,
        screenName: String,
        androidComponentIndex: Int,
        module: Module,
        category: Category
    ) {
        writeActionDispatcher.dispatch {
            fileCreator.createScreenFiles(
                packageName,
                screenName,
                AndroidComponent.values()[androidComponentIndex],
                module,
                category
            )
        }
        effect.push(NewScreenEffect.Close)
    }

    private fun MutableSharedFlow<NewScreenEffect>.push(effect: NewScreenEffect) {
        scope.launch { emit(effect) }
    }

    fun onCleared() {
        scope.cancel()
    }
}