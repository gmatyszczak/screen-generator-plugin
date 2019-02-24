package ui.help

import com.intellij.ui.layout.panel
import model.Variable
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JTextField

class HelpPanel : JPanel() {

    init {
        layout = BorderLayout()
        val panel = panel {
            row { label("Available variables in templates:") }
            Variable.values().forEach {
                row {
                    JTextField(it.value).apply { isEditable = false }()
                    label(" - ${it.description}")
                }
            }
        }
        add(panel, BorderLayout.CENTER)
    }
}