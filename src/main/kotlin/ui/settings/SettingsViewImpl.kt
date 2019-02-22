package ui.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import data.repository.SettingsRepositoryImpl
import model.ScreenElement
import util.addTextChangeListener
import javax.swing.JComponent
import javax.swing.event.DocumentListener

class SettingsViewImpl(project: Project) : Configurable, SettingsView {

    private val panel = SettingsJPanel(project)
    private val presenter = SettingsPresenter(this, SettingsRepositoryImpl(project))
    private var nameDocumentListener: DocumentListener? = null
    private var templateDocumentListener: com.intellij.openapi.editor.event.DocumentListener? = null
    private var activityDocumentListener: DocumentListener? = null
    private var fragmentDocumentListener: DocumentListener? = null

    override fun isModified() = presenter.isModified

    override fun getDisplayName() = "Screen Generator Plugin"

    override fun apply() = presenter.onApplySettings()

    override fun reset() = presenter.onResetSettings()

    override fun createComponent(): JComponent {
        presenter.onLoadView()
        panel.create()
        return panel
    }

    override fun setUpListeners() {
        panel.toolbarDecorator.setAddAction { presenter.onAddClick() }
        panel.toolbarDecorator.setRemoveAction { presenter.onDeleteClick(panel.list.selectedIndex) }
        panel.toolbarDecorator.setMoveDownAction { presenter.onMoveDownClick(panel.list.selectedIndex) }
        panel.toolbarDecorator.setMoveUpAction { presenter.onMoveUpClick(panel.list.selectedIndex) }
        panel.list.addListSelectionListener {
            if (!it.valueIsAdjusting) presenter.onScreenElementSelect(panel.list.selectedIndex)
        }
    }

    override fun addScreenElement(screenElement: ScreenElement) {
        panel.listModel.add(screenElement)
    }

    override fun selectScreenElement(index: Int) {
        panel.list.selectedIndex = index
    }

    override fun showName(name: String) {
        panel.nameTextField.text = name
    }

    override fun addTextChangeListeners() {
        nameDocumentListener = panel.nameTextField.addTextChangeListener(presenter::onNameChange)
        templateDocumentListener = panel.templateEditorTextField.addTextChangeListener(presenter::onTemplateChange)
    }

    override fun removeTextChangeListeners() {
        nameDocumentListener?.let { panel.nameTextField.document.removeDocumentListener(it) }
        templateDocumentListener?.let { panel.templateEditorTextField.document.removeDocumentListener(it) }
        nameDocumentListener = null
        templateDocumentListener = null
    }

    override fun updateScreenElement(index: Int, screenElement: ScreenElement) {
        panel.listModel.setElementAt(screenElement, index)
    }

    override fun removeScreenElement(index: Int) {
        panel.listModel.remove(index)
    }

    override fun showScreenElements(screenElements: List<ScreenElement>) =
            screenElements.forEach { panel.listModel.add(it) }

    override fun clearScreenElements() = panel.listModel.removeAll()

    override fun showSampleCode(text: String) {
        panel.sampleEditorTextField.text = text
    }

    override fun showTemplate(template: String) {
        panel.templateEditorTextField.text = template
    }

    override fun showActivityBaseClass(text: String) {
        panel.activityTextField.text = text
    }

    override fun showFragmentBaseClass(text: String) {
        panel.fragmentTextField.text = text
    }

    override fun addBaseClassTextChangeListeners() {
        activityDocumentListener = panel.activityTextField.addTextChangeListener(presenter::onActivityBaseClassChange)
        fragmentDocumentListener = panel.fragmentTextField.addTextChangeListener(presenter::onFragmentBaseClassChange)
    }

    override fun removeBaseClassTextChangeListeners() {
        activityDocumentListener?.let { panel.activityTextField.document.removeDocumentListener(it) }
        activityDocumentListener = null
        fragmentDocumentListener?.let { panel.fragmentTextField.document.removeDocumentListener(it) }
        fragmentDocumentListener = null
    }
}