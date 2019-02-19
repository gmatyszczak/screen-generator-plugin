package newscreen

import newscreen.files.ProjectStructure

class NewScreenPresenter(private val view: NewScreenView,
                         private val fileCreator: FileCreator,
                         private val projectStructure: ProjectStructure) {

    fun onOkClick(packageName: String, screenName: String) {
        val sourceRoots = projectStructure.findSourceRoots().filter {
            !it.path.contains("build", true)
                    && !it.path.contains("test", true)
                    && !it.path.contains("res", true)
        }
        fileCreator.createScreenFiles(sourceRoots[0], packageName, screenName)
        view.close()
    }
}