package ui.newscreen

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import data.file.*
import data.repository.SettingsRepositoryImpl
import data.repository.SourceRootRepositoryImpl
import model.AndroidComponent
import javax.swing.JComponent

class NewScreenDialog(project: Project, currentPath: CurrentPath?) : DialogWrapper(true), NewScreenView {

    private val panel = NewScreenPanel()

    private val presenter: NewScreenPresenter

    init {
        val projectStructure = ProjectStructureImpl(project)
        val sourceRootRepository = SourceRootRepositoryImpl(projectStructure)
        val fileCreator = FileCreatorImpl(SettingsRepositoryImpl(project), sourceRootRepository)
        val packageExtractor = PackageExtractorImpl(currentPath, sourceRootRepository)
        val writeActionDispatcher = WriteActionDispatcherImpl()
        presenter = NewScreenPresenter(this, fileCreator, packageExtractor, writeActionDispatcher)
        init()
    }

    override fun doOKAction() = presenter.onOkClick(panel.packageTextField.text, panel.nameTextField.text, AndroidComponent.values()[panel.androidComponentComboBox.selectedIndex])

    override fun createCenterPanel(): JComponent {
        presenter.onLoadView()
        return panel
    }

    override fun close() = close(DialogWrapper.OK_EXIT_CODE)

    override fun showPackage(packageName: String) {
        panel.packageTextField.text = packageName
    }
}