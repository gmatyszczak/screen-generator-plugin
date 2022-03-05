package ui.settings.widget

import com.intellij.ui.IdeBorderFactory
import ui.settings.SettingsState
import util.addTextChangeListener
import util.updateText
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class CustomVariableDetailsPanel : JPanel() {

    var onNameTextChanged: ((String) -> Unit)? = null

    private val nameTextField = JTextField()

    private var listenersBlocked = false

    init {
        border = IdeBorderFactory.createTitledBorder("Custom Variable Details", false)
        layout = GridBagLayout()

        add(JLabel("Custom Variable Name:"), constraintsLeft(0, 0))
        add(nameTextField, constraintsRight(1, 0))

        nameTextField.addTextChangeListener { if (!listenersBlocked) onNameTextChanged?.invoke(it) }
    }

    fun render(state: SettingsState) {
        listenersBlocked = true
        val selectedCustomVariable = state.selectedCustomVariable
        nameTextField.updateText(selectedCustomVariable?.name ?: "")

        isEnabled = selectedCustomVariable != null
        components.forEach { it.isEnabled = selectedCustomVariable != null }
        listenersBlocked = false
    }
}
