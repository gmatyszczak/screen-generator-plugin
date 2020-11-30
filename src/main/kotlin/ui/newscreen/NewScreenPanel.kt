package ui.newscreen

import com.intellij.openapi.ui.ComboBox
import model.AndroidComponent
import model.Category
import model.Module
import ui.settings.widget.constraintsLeft
import ui.settings.widget.constraintsRight
import util.updateText
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField


class NewScreenPanel : JPanel() {

    val nameTextField = JTextField()
    val packageTextField = JTextField()

    val categoryComboBox = ComboBox<Category>()
    val androidComponentComboBox = ComboBox(AndroidComponent.values())
    val moduleComboBox = ComboBox<Module>()
    val customVariablesPanel = CustomVariablesPanel()

    var onCategoryIndexChanged: ((Int) -> Unit)? = null

    private var listenersBlocked = false

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(JPanel().apply {
            layout = GridBagLayout()
            add(JLabel("Name:"), constraintsLeft(0, 0))
            add(nameTextField, constraintsRight(1, 0))
            add(JLabel("Category:"), constraintsLeft(0, 1))
            add(categoryComboBox, constraintsRight(1, 1))
            add(JLabel("Module:"), constraintsLeft(0, 2))
            add(moduleComboBox, constraintsRight(1, 2))
            add(JLabel("Package:"), constraintsLeft(0, 3))
            add(packageTextField, constraintsRight(1, 3))
            add(JLabel("Android Component:"), constraintsLeft(0, 4))
            add(androidComponentComboBox, constraintsRight(1, 4))
            categoryComboBox.addActionListener { if (!listenersBlocked) onCategoryIndexChanged?.invoke(categoryComboBox.selectedIndex) }
        })
        add(customVariablesPanel)
    }

    override fun getPreferredSize() = Dimension(350, 110)

    fun render(state: NewScreenState) = state.run {
        listenersBlocked = true
        packageTextField.updateText(packageName)

        if (moduleComboBox.itemCount == 0) {
            moduleComboBox.removeAllItems()
            modules.forEach { moduleComboBox.addItem(it) }
        }

        moduleComboBox.selectedItem = selectedModule

        if (categoryComboBox.itemCount == 0) {
            categories.forEach { categoryComboBox.addItem(it) }
        }

        customVariablesPanel.render(state)
        listenersBlocked = false
    }
}
