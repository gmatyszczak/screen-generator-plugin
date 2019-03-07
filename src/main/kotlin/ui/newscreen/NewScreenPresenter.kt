package ui.newscreen

import data.file.CurrentPath
import data.file.FileCreator
import data.file.PackageExtractor
import data.file.WriteActionDispatcher
import data.repository.ModuleRepository
import model.AndroidComponent

class NewScreenPresenter(private val view: NewScreenView,
                         private val fileCreator: FileCreator,
                         private val packageExtractor: PackageExtractor,
                         private val writeActionDispatcher: WriteActionDispatcher,
                         private val moduleRepository: ModuleRepository,
                         private val currentPath: CurrentPath?) {

    fun onLoadView() {
        view.showPackage(packageExtractor.extractFromCurrentPath())
        view.showModules(moduleRepository.getAllModules())
        currentPath?.let { view.selectModule(currentPath.module) }
    }

    fun onOkClick(packageName: String, screenName: String, androidComponent: AndroidComponent) {
        writeActionDispatcher.dispatch {
            fileCreator.createScreenFiles(packageName, screenName, androidComponent)
        }
        view.close()
    }
}