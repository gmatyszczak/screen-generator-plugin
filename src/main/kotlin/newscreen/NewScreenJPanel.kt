package newscreen

import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class NewScreenJPanel : JPanel() {

    val nameTextField = JTextField()

    init {
        layout = null
        JLabel("Name: ").addAt(20, 50, 80, 16)
        nameTextField.addAt(100, 45, 180, 26)
    }

    override fun getPreferredSize() = Dimension(300, 110)

    private fun JComponent.addAt(x: Int, y: Int, width: Int, height: Int) {
        setBounds(x, y, width, height)
        this@NewScreenJPanel.add(this)
    }
}
