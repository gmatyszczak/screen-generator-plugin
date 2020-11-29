package ui.newscreen

import com.intellij.openapi.ui.ComboBox
import model.AndroidComponent
import model.Category
import model.Module
import java.awt.Dimension
import java.awt.GridLayout
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

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(JPanel().apply {
            layout = GridLayout(0, 2)
            add(JLabel("Name:"))
            add(nameTextField)
            add(JLabel("Category:"))
            add(categoryComboBox)
            add(JLabel("Module:"))
            add(moduleComboBox)
            add(JLabel("Package:"))
            add(packageTextField)
            add(JLabel("Android Component:"))
            add(androidComponentComboBox)
        })
        add(customVariablesPanel)
    }

    override fun getPreferredSize() = Dimension(350, 110)

    fun render(state: NewScreenState) = state.run {
        packageTextField.text = packageName
        moduleComboBox.removeAllItems()
        modules.forEach { moduleComboBox.addItem(it) }
        categories.forEach { categoryComboBox.addItem(it) }
        moduleComboBox.selectedItem = selectedModule
        customVariablesPanel.render(state)
    }
}
