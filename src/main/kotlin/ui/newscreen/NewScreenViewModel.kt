package ui.newscreen

import data.file.CurrentPath
import data.file.FileCreator
import data.file.PackageExtractor
import data.file.WriteActionDispatcher
import data.repository.ModuleRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.AndroidComponent
import model.Module

class NewScreenViewModel(
    private val fileCreator: FileCreator,
    packageExtractor: PackageExtractor,
    private val writeActionDispatcher: WriteActionDispatcher,
    moduleRepository: ModuleRepository,
    currentPath: CurrentPath?
) {

    private val scope = MainScope()

    val state = MutableStateFlow(
        NewScreenState(
            packageName = packageExtractor.extractFromCurrentPath(),
            modules = moduleRepository.getAllModules(),
            selectedModule = currentPath?.module
        )
    )

    val effect = MutableSharedFlow<NewScreenEffect>(replay = 0)

    fun onOkClick(packageName: String, screenName: String, androidComponentIndex: Int, module: Module) {
        writeActionDispatcher.dispatch {
            fileCreator.createScreenFiles(
                packageName,
                screenName,
                AndroidComponent.values()[androidComponentIndex],
                module
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