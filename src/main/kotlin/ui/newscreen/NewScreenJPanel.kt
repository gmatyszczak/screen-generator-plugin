package ui.newscreen

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.layout.panel
import model.AndroidComponent
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JPanel
import javax.swing.JTextField


class NewScreenJPanel : JPanel() {

    val nameTextField = JTextField()
    val packageTextField = JTextField()

    val androidComponentComboBox = ComboBox<AndroidComponent>(AndroidComponent.values())

    init {
        layout = BorderLayout()
        val panel = panel {
            row("Package:") { packageTextField() }
            row("Name:") { nameTextField() }
            row("Android Component:") { androidComponentComboBox() }
        }
        add(panel, BorderLayout.CENTER)
    }

    override fun getPreferredSize() = Dimension(450, 110)
}
