package newscreen

class NewScreenPresenter(private val view: NewScreenView,
                         private val fileCreator: FileCreator,
                         private val sourceRootRepository: SourceRootRepository,
                         private val packageExtractor: PackageExtractor) {

    fun onLoadView() {
        view.showPackage(packageExtractor.extractFromCurrentPath())
    }

    fun onOkClick(packageName: String, screenName: String) {
        fileCreator.createScreenFiles(sourceRootRepository.findFirstModuleSourceRoot(), packageName, screenName)
        view.close()
    }
}