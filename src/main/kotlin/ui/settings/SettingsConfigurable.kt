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

        panel.screenElementsPanel.onAddClicked = { viewModel.reduce(SettingsAction.AddScreenElement) }
        panel.screenElementsPanel.onRemoveClicked = { viewModel.reduce(SettingsAction.RemoveScreenElement(it)) }
        panel.screenElementsPanel.onMoveDownClicked = { viewModel.reduce(SettingsAction.MoveDownScreenElement(it)) }
        panel.screenElementsPanel.onMoveUpClicked = { viewModel.reduce(SettingsAction.MoveUpScreenElement(it)) }
        panel.screenElementsPanel.onItemSelected = { viewModel.reduce(SettingsAction.SelectScreenElement(it)) }

        panel.screenElementDetailsPanel.onNameTextChanged = { viewModel.reduce(SettingsAction.ChangeName(it)) }
        panel.screenElementDetailsPanel.onFileNameTextChanged = { viewModel.reduce(SettingsAction.ChangeFileName(it)) }
        panel.screenElementDetailsPanel.onFileTypeIndexChanged = { viewModel.reduce(SettingsAction.ChangeFileType(it)) }

        panel.onTemplateTextChanged = { viewModel.reduce(SettingsAction.ChangeTemplate(it)) }
        panel.onHelpClicked = { viewModel.reduce(SettingsAction.ClickHelp) }

        panel.androidComponentsPanel.onActivityTextChanged = { viewModel.reduce(SettingsAction.ChangeActivity(it)) }
        panel.androidComponentsPanel.onFragmentTextChanged = { viewModel.reduce(SettingsAction.ChangeFragment(it)) }

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