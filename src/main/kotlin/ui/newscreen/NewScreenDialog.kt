package ui.newscreen

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import data.file.CurrentPath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Category
import model.Module
import ui.core.UI
import ui.newscreen.NewScreenAction.CategoryIndexChanged
import ui.newscreen.NewScreenAction.OkClicked
import ui.newscreen.dagger.DaggerNewScreenComponent
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NewScreenDialog(project: Project, currentPath: CurrentPath?) : DialogWrapper(true), CoroutineScope {

    @Inject
    lateinit var viewModel: NewScreenViewModel

    private val job = SupervisorJob()
    private val panel = NewScreenPanel()

    override val coroutineContext: CoroutineContext = Dispatchers.UI + job

    init {
        DaggerNewScreenComponent.factory().create(project, currentPath).inject(this)
        launch { viewModel.state.collect { it.render() } }
        launch { viewModel.effect.collect { handleEffect(it) } }
        panel.onCategoryIndexChanged = { launch { viewModel.actionFlow.emit(CategoryIndexChanged(it)) } }
        init()
    }

    private fun handleEffect(effect: NewScreenEffect) = when (effect) {
        NewScreenEffect.Close -> close(OK_EXIT_CODE)
    }

    private fun NewScreenState.render() = panel.render(this)

    override fun doOKAction() {
        launch {
            viewModel.actionFlow.emit(
                OkClicked(
                    panel.packageTextField.text,
                    panel.nameTextField.text,
                    panel.androidComponentComboBox.selectedIndex,
                    panel.moduleComboBox.selectedItem as Module,
                    panel.categoryComboBox.selectedItem as Category,
                    panel.customVariablesPanel.customVariablesMap
                )
            )
        }
    }

    override fun createCenterPanel() = panel

    override fun dispose() {
        job.cancel()
        viewModel.clear()
        super.dispose()
    }
}
