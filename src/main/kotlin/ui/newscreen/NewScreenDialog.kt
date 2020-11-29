package ui.newscreen

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import data.file.CurrentPath
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Category
import model.Module
import ui.newscreen.dagger.DaggerNewScreenComponent
import javax.inject.Inject

class NewScreenDialog(project: Project, currentPath: CurrentPath?) : DialogWrapper(true) {

    private val scope = MainScope()

    private val panel = NewScreenPanel()

    @Inject
    lateinit var viewModel: NewScreenViewModel

    init {
        DaggerNewScreenComponent.factory().create(project, currentPath).inject(this)
        scope.launch { viewModel.state.collect { it.render() } }
        scope.launch { viewModel.effect.collect { handleEffect(it) } }
        init()
    }

    private fun handleEffect(effect: NewScreenEffect) = when(effect) {
        NewScreenEffect.Close -> close(OK_EXIT_CODE)
    }

    private fun NewScreenState.render() = panel.render(this)

    override fun doOKAction() =
        viewModel.onOkClick(
            panel.packageTextField.text,
            panel.nameTextField.text,
            panel.androidComponentComboBox.selectedIndex,
            panel.moduleComboBox.selectedItem as Module,
            panel.categoryComboBox.selectedItem as Category
        )

    override fun createCenterPanel() = panel

    override fun dispose() {
        viewModel.onCleared()
        scope.cancel()
        super.dispose()
    }
}
