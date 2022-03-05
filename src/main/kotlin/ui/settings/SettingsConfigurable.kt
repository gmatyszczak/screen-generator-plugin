package ui.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ui.help.HelpDialog
import ui.settings.dagger.DaggerSettingsComponent
import ui.settings.widget.SettingsPanel
import javax.inject.Inject
import javax.swing.JComponent

class SettingsConfigurable(private val project: Project) : Configurable {

    private lateinit var panel: SettingsPanel

    @Inject
    lateinit var state: MutableStateFlow<SettingsState>

    @Inject
    lateinit var effect: MutableSharedFlow<SettingsEffect>

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var viewModel: SettingsViewModel

    override fun isModified() = if (::state.isInitialized) state.value.isModified else false

    override fun getDisplayName() = "Screen Generator Plugin"

    override fun apply() = viewModel.reduce(SettingsAction.ApplySettings)

    override fun reset() = viewModel.reduce(SettingsAction.ResetSettings)

    override fun createComponent(): JComponent? {
        DaggerSettingsComponent.factory().create(project).inject(this)
        panel = SettingsPanel(project)

        panel.categoriesPanel.onAddClicked = { viewModel.reduce(SettingsAction.AddCategory) }
        panel.categoriesPanel.onRemoveClicked = { viewModel.reduce(SettingsAction.RemoveCategory(it)) }
        panel.categoriesPanel.onMoveUpClicked = { viewModel.reduce(SettingsAction.MoveUpCategory(it)) }
        panel.categoriesPanel.onMoveDownClicked = { viewModel.reduce(SettingsAction.MoveDownCategory(it)) }
        panel.categoriesPanel.onItemSelected = { viewModel.reduce(SettingsAction.SelectCategory(it)) }

        panel.categoryDetailsPanel.onNameTextChanged = { viewModel.reduce(SettingsAction.ChangeCategoryName(it)) }

        panel.customVariableDetailsPanel.onNameTextChanged = { viewModel.reduce(SettingsAction.ChangeCustomVariableName(it)) }

        panel.customVariablesPanel.onAddClicked = { viewModel.reduce(SettingsAction.AddCustomVariable) }
        panel.customVariablesPanel.onRemoveClicked = { viewModel.reduce(SettingsAction.RemoveCustomVariable(it)) }
        panel.customVariablesPanel.onMoveUpClicked = { viewModel.reduce(SettingsAction.MoveUpCustomVariable(it)) }
        panel.customVariablesPanel.onMoveDownClicked = { viewModel.reduce(SettingsAction.MoveDownCustomVariable(it)) }
        panel.customVariablesPanel.onItemSelected = { viewModel.reduce(SettingsAction.SelectCustomVariable(it)) }

        panel.screenElementsPanel.onAddClicked = { viewModel.reduce(SettingsAction.AddScreenElement) }
        panel.screenElementsPanel.onRemoveClicked = { viewModel.reduce(SettingsAction.RemoveScreenElement(it)) }
        panel.screenElementsPanel.onMoveDownClicked = { viewModel.reduce(SettingsAction.MoveDownScreenElement(it)) }
        panel.screenElementsPanel.onMoveUpClicked = { viewModel.reduce(SettingsAction.MoveUpScreenElement(it)) }
        panel.screenElementsPanel.onItemSelected = { viewModel.reduce(SettingsAction.SelectScreenElement(it)) }

        panel.screenElementDetailsPanel.onNameTextChanged = { viewModel.reduce(SettingsAction.ChangeName(it)) }
        panel.screenElementDetailsPanel.onFileNameTextChanged = { viewModel.reduce(SettingsAction.ChangeFileName(it)) }
        panel.screenElementDetailsPanel.onFileTypeIndexChanged = { viewModel.reduce(SettingsAction.ChangeFileType(it)) }
        panel.screenElementDetailsPanel.onSubdirectoryTextChanged =
            { viewModel.reduce(SettingsAction.ChangeSubdirectory(it)) }
        panel.screenElementDetailsPanel.onSourceSetTextChanged =
            { viewModel.reduce(SettingsAction.ChangeSourceSet(it)) }
        panel.screenElementDetailsPanel.onAndroidComponentIndexChanged =
            { viewModel.reduce(SettingsAction.ChangeAndroidComponent(it)) }

        panel.onTemplateTextChanged = { viewModel.reduce(SettingsAction.ChangeTemplate(it)) }
        panel.onHelpClicked = { viewModel.reduce(SettingsAction.ClickHelp) }

        scope.launch { state.collect { panel.render(it) } }
        scope.launch { effect.collect { it.handle() } }

        return panel
    }

    override fun disposeUIResources() {
        scope.cancel()
        super.disposeUIResources()
    }

    private fun SettingsEffect.handle() = when (this) {
        is SettingsEffect.SelectScreenElement -> panel.screenElementsPanel.updateSelectedIndex(index)
        SettingsEffect.ShowHelp -> HelpDialog().show()
    }
}
