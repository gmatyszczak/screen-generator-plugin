package ui.newscreen

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import data.file.CurrentPath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Category
import model.Module
import ui.newscreen.dagger.DaggerNewScreenComponent
import javax.inject.Inject

class NewScreenDialog(project: Project, currentPath: CurrentPath?) : DialogWrapper(true) {

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var state: MutableStateFlow<NewScreenState>

    @Inject
    lateinit var effect: MutableSharedFlow<NewScreenEffect>

    @Inject
    lateinit var viewModel: NewScreenViewModel

    private val panel = NewScreenPanel()

    init {
        DaggerNewScreenComponent.factory().create(project, currentPath).inject(this)
        scope.launch { state.collect { it.render() } }
        scope.launch { effect.collect { handleEffect(it) } }
        panel.onCategoryIndexChanged = { viewModel.reduce(NewScreenAction.CategoryIndexChanged(it)) }
        init()
    }

    private fun handleEffect(effect: NewScreenEffect) = when (effect) {
        NewScreenEffect.Close -> close(OK_EXIT_CODE)
    }

    private fun NewScreenState.render() = panel.render(this)

    override fun doOKAction() =
        viewModel.reduce(
            NewScreenAction.OkClicked(
                panel.packageTextField.text,
                panel.nameTextField.text,
                panel.androidComponentComboBox.selectedIndex,
                panel.moduleComboBox.selectedItem as Module,
                panel.categoryComboBox.selectedItem as Category
            )
        )

    override fun createCenterPanel() = panel

    override fun dispose() {
        scope.cancel()
        super.dispose()
    }
}
