package newscreen

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import newscreen.files.ProjectStructureImpl
import settings.SettingsRepositoryImpl

class NewScreenDialog(project: Project) : DialogWrapper(true), NewScreenView {

    private val panel = NewScreenJPanel()
    private val presenter = NewScreenPresenter(this, FileCreatorImpl(SettingsRepositoryImpl(project)), ProjectStructureImpl(project))

    init {
        init()
    }

    override fun doOKAction() = presenter.onOkClick(panel.packageTextField.text, panel.nameTextField.text)

    override fun createCenterPanel() = panel

    override fun close() = close(DialogWrapper.OK_EXIT_CODE)
}