package ui.newscreen

import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class CustomVariablesPanel : JPanel() {


    init {
        layout = GridLayout(0, 2)
    }

    fun render(state: NewScreenState) {
        state.selectedCategory.customVariables.forEach {
            add(JLabel(it.name))
            add(JTextField())
        }
    }
}