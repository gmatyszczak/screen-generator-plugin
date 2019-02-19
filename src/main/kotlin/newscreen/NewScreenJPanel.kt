package newscreen

import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField


class NewScreenJPanel : JPanel() {

    val nameTextField = JTextField()
    val packageTextField = JTextField()

    init {
        layout = GridBagLayout()
        val constraints = GridBagConstraints()
        constraints.apply {
            fill = GridBagConstraints.NONE
            gridx = 0
            gridy = 0
            weightx = 0.2
        }
        add(JLabel("Package: "), constraints)

        constraints.apply {
            fill = GridBagConstraints.HORIZONTAL
            gridx = 1
            gridy = 0
            weightx = 0.8
        }
        add(packageTextField, constraints)

        constraints.apply {
            fill = GridBagConstraints.NONE
            gridx = 0
            gridy = 1
            weightx = 0.2
        }
        add(JLabel("Name: "), constraints)

        constraints.apply {
            fill = GridBagConstraints.HORIZONTAL
            gridx = 1
            gridy = 1
            weightx = 0.8
        }
        add(nameTextField, constraints)
    }

    override fun getPreferredSize() = Dimension(300, 110)
}
