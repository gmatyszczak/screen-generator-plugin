package ui.newscreen

import com.intellij.util.ui.FormBuilder
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JPanel
import javax.swing.JTextField


class NewScreenJPanel : JPanel() {

    val nameTextField = JTextField()
    val packageTextField = JTextField()

    init {
        layout = BorderLayout()
        val builder = FormBuilder.createFormBuilder()
                .addLabeledComponent("Package:", packageTextField)
                .addLabeledComponent("Name:", nameTextField)
        add(builder.panel, BorderLayout.CENTER)
    }

    override fun getPreferredSize() = Dimension(300, 110)
}
