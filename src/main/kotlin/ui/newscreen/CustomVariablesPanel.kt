package ui.newscreen

import com.intellij.ui.IdeBorderFactory
import model.CustomVariable
import ui.settings.widget.constraintsLeft
import ui.settings.widget.constraintsRight
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class CustomVariablesPanel : JPanel() {

    private var textFieldsMap: Map<CustomVariable, JTextField> = emptyMap()

    val customVariablesMap
        get() = textFieldsMap.keys.associateWith { (textFieldsMap[it] ?: error("")).text }

    init {
        layout = GridBagLayout()
        border = IdeBorderFactory.createTitledBorder("Custom Variables", false)
        isVisible = false
    }

    fun render(state: NewScreenState) {
        if (textFieldsMap.keys != state.selectedCategory?.customVariables) {
            isVisible = state.selectedCategory?.customVariables?.isNotEmpty() ?: false
            removeAll()
            val newMap = mutableMapOf<CustomVariable, JTextField>()
            state.selectedCategory?.customVariables?.forEachIndexed { index, customVariable ->
                val textField = JTextField()
                add(JLabel(customVariable.name), constraintsLeft(0, index))
                add(textField, constraintsRight(1, index))
                newMap[customVariable] = textField
            }
            textFieldsMap = newMap
            revalidate()
            repaint()
        }
    }
}
