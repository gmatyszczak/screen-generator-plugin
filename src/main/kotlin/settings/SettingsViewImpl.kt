package settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class SettingsViewImpl : Configurable, SettingsView {

    private val panel = SettingsJPanel()
    private val presenter = SettingsPresenter(this)
    private var isModified = false
    private var nameDocumentListener: DocumentListener? = null

    override fun isModified() = isModified

    override fun getDisplayName() = "Screen Generator Plugin"

    override fun apply() {
        // TODO
    }

    override fun createComponent(): JComponent {
        presenter.onLoadView()
        return panel
    }

    override fun setUpListeners() {
        panel.addButton.addActionListener { presenter.onAddClick() }
        panel.deleteButton.addActionListener {
            presenter.onDeleteClick(panel.list.selectedIndex)
        }
        panel.list.addListSelectionListener {
            if (!it.valueIsAdjusting) presenter.onScreenElementSelect(panel.list.selectedIndex)
        }
    }

    override fun addScreenElement(screenElement: ScreenElement) {
        panel.listModel.addElement(screenElement)
    }

    override fun selectLastScreenElement() {
        panel.list.selectedIndex = panel.listModel.size() - 1
    }

    override fun showName(name: String) {
        panel.nameTextField.text = name
    }

    override fun addNameChangeListener() {
        nameDocumentListener = panel.nameTextField.addTextChangeListener(presenter::onNameChange)
    }

    override fun removeCurrentNameChangeListener() {
        nameDocumentListener?.let { panel.nameTextField.document.removeDocumentListener(it) }
        nameDocumentListener = null
    }

    override fun updateScreenElement(index: Int, screenElement: ScreenElement) {
        panel.listModel.set(index, screenElement)
    }

    override fun removeScreenElement(index: Int) {
        panel.listModel.remove(index)
    }

    private fun JTextField.addTextChangeListener(onChange: (String) -> Unit) =
            object : DocumentListener {
                override fun changedUpdate(e: DocumentEvent?) = onChange(text)
                override fun insertUpdate(e: DocumentEvent?) = onChange(text)
                override fun removeUpdate(e: DocumentEvent?) = onChange(text)
            }.apply { document.addDocumentListener(this) }
}