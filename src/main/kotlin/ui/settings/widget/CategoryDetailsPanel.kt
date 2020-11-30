package ui.settings.widget

import com.intellij.ui.IdeBorderFactory
import ui.settings.SettingsState
import util.addTextChangeListener
import util.updateText
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class CategoryDetailsPanel : JPanel() {

    var onNameTextChanged: ((String) -> Unit)? = null

    private val nameTextField = JTextField()

    private var listenersBlocked = false

    init {
        border = IdeBorderFactory.createTitledBorder("Category Details", false)
        layout = GridBagLayout()

        add(JLabel("Category Name:"), constraintsLeft(0, 0))
        add(nameTextField, constraintsRight(1, 0))

        nameTextField.addTextChangeListener { if (!listenersBlocked) onNameTextChanged?.invoke(it) }
    }

    fun render(state: SettingsState) {
        listenersBlocked = true
        val selectedCategory = state.selectedCategoryScreenElements
        nameTextField.updateText(selectedCategory?.category?.name ?: "")

        isEnabled = selectedCategory != null
        components.forEach { it.isEnabled = selectedCategory != null }
        listenersBlocked = false
    }
}


