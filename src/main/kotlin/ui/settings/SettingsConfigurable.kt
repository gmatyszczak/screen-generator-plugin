package ui.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import data.repository.SettingsRepositoryImpl
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ui.help.HelpDialog
import ui.settings.reducer.*
import ui.settings.widget.SettingsPanel
import javax.swing.JComponent

class SettingsConfigurable(private val project: Project) : Configurable {

    private val scope = MainScope()

    private lateinit var panel: SettingsPanel

    private lateinit var state: MutableStateFlow<SettingsState>

    private lateinit var viewModel: SettingsViewModel

    override fun isModified() = if (::state.isInitialized) state.value.isModified else false

    override fun getDisplayName() = "Screen Generator Plugin"

    override fun apply() = viewModel.reduce(SettingsAction.ApplySettings)

    override fun reset() = viewModel.reduce(SettingsAction.ResetSettings)

    override fun createComponent(): JComponent? {
        panel = SettingsPanel(project)
        state = MutableStateFlow(SettingsState())
        val effect = MutableSharedFlow<SettingsEffect>(replay = 0)
        val settingsRepository = SettingsRepositoryImpl(project)
        val selectScreenElementReducer = SelectScreenElementReducerImpl(state, effect, scope)
        val updateScreenElementReducer = UpdateScreenElementReducerImpl(state, effect, scope)
        viewModel = SettingsViewModel(
            ApplySettingsReducerImpl(state, effect, scope, settingsRepository),
            ResetSettingsReducerImpl(state, effect, scope, settingsRepository),
            AddScreenElementReducerImpl(state, effect, scope, selectScreenElementReducer),
            RemoveScreenElementReducerImpl(state, effect, scope, selectScreenElementReducer),
            MoveDownScreenElementReducerImpl(state, effect, scope, selectScreenElementReducer),
            MoveUpScreenElementReducerImpl(state, effect, scope, selectScreenElementReducer),
            selectScreenElementReducer,
            ChangeNameReducerImpl(state, effect, scope, updateScreenElementReducer),
            ChangeFileNameReducerImpl(state, effect, scope, updateScreenElementReducer),
            ChangeTemplateReducerImpl(state, effect, scope, updateScreenElementReducer),
            ChangeFileTypeReducerImpl(state, effect, scope, updateScreenElementReducer),
            ChangeActivityReducerImpl(state, effect, scope),
            ChangeFragmentReducerImpl(state, effect, scope),
            ClickHelpReducerImpl(state, effect, scope)
        )

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
        is SettingsEffect.SelectScreenElement -> {
            panel.screenElementsPanel.updateSelectedIndex(index)
        }
        SettingsEffect.ShowHelp -> HelpDialog().show()
    }
}