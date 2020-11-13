package ui.newscreen

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import data.file.*
import data.repository.ModuleRepositoryImpl
import data.repository.SettingsRepositoryImpl
import data.repository.SourceRootRepositoryImpl
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Module

class NewScreenDialog(project: Project, currentPath: CurrentPath?) : DialogWrapper(true) {

    private val scope = MainScope()

    private val panel = NewScreenPanel()

    private val viewModel: NewScreenViewModel

    init {
        val projectStructure = ProjectStructureImpl(project)
        val sourceRootRepository = SourceRootRepositoryImpl(projectStructure)
        val fileCreator = FileCreatorImpl(SettingsRepositoryImpl(project), sourceRootRepository)
        val packageExtractor = PackageExtractorImpl(currentPath, sourceRootRepository)
        val writeActionDispatcher = WriteActionDispatcherImpl(project)
        val moduleRepository = ModuleRepositoryImpl(projectStructure)
        viewModel =
            NewScreenViewModel(fileCreator, packageExtractor, writeActionDispatcher, moduleRepository, currentPath)
        scope.launch { viewModel.state.collect { it.render() } }
        scope.launch { viewModel.effect.collect { handleEffect(it) } }
        init()
    }

    private fun handleEffect(effect: NewScreenEffect) = when(effect) {
        NewScreenEffect.Close -> close(DialogWrapper.OK_EXIT_CODE)
    }

    private fun NewScreenState.render() {
        panel.packageTextField.text = packageName
        panel.moduleComboBox.removeAllItems()
        modules.forEach { panel.moduleComboBox.addItem(it) }
        panel.moduleComboBox.selectedItem = selectedModule
    }

    override fun doOKAction() =
        viewModel.onOkClick(
            panel.packageTextField.text,
            panel.nameTextField.text,
            panel.androidComponentComboBox.selectedIndex,
            panel.moduleComboBox.selectedItem as Module
        )

    override fun createCenterPanel() = panel

    override fun dispose() {
        viewModel.onCleared()
        scope.cancel()
        super.dispose()
    }
}
