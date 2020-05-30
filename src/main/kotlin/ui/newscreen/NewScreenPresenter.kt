package ui.newscreen

import data.file.CurrentPath
import data.file.FileCreator
import data.file.PackageExtractor
import data.file.WriteActionDispatcher
import data.repository.ModuleRepository
import data.repository.SettingsRepositoryImpl
import model.AndroidComponent
import model.Category

class NewScreenPresenter(
        private val view: NewScreenView,
        private val settings: SettingsRepositoryImpl,
        private val fileCreator: FileCreator,
        private val packageExtractor: PackageExtractor,
        private val writeActionDispatcher: WriteActionDispatcher,
        private val moduleRepository: ModuleRepository,
        private val currentPath: CurrentPath?
) {

    fun onLoadView() {
        view.showCategories(settings.loadSettings().categories)
        view.showPackage(packageExtractor.extractFromCurrentPath())
        view.showModules(moduleRepository.getAllModules())
        currentPath?.let { view.selectModule(currentPath.module) }
    }

    fun onOkClick(category: Category, packageName: String, screenName: String, androidComponent: AndroidComponent, module: String) {
        writeActionDispatcher.dispatch {
            fileCreator.createScreenFiles(category, packageName, screenName, androidComponent, module)
        }
        view.close()
    }
}