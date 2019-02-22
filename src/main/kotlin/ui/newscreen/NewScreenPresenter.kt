package ui.newscreen

import data.file.FileCreator
import data.file.PackageExtractor
import data.file.WriteActionDispatcher
import data.repository.SourceRootRepository

class NewScreenPresenter(private val view: NewScreenView,
                         private val fileCreator: FileCreator,
                         private val sourceRootRepository: SourceRootRepository,
                         private val packageExtractor: PackageExtractor,
                         private val writeActionDispatcher: WriteActionDispatcher) {

    fun onLoadView() {
        view.showPackage(packageExtractor.extractFromCurrentPath())
    }

    fun onOkClick(packageName: String, screenName: String) {
        writeActionDispatcher.dispatch {
            fileCreator.createScreenFiles(sourceRootRepository.findFirstModuleSourceRoot(), packageName, screenName)
        }
        view.close()
    }
}