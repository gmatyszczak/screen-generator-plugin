package settings

import com.intellij.ui.components.JBList
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.*

class SettingsJPanel : JPanel() {

    val deleteButton = JButton("-")
    val addButton = JButton("+")
    val nameTextField = JTextField()

    val listModel = DefaultListModel<ScreenElement>()
    val list = JBList<ScreenElement>(listModel).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
    }

    init {
        layout = GridLayout(1, 2)

        val leftPanel = JPanel(BorderLayout())
        leftPanel.add(JPanel(FlowLayout(FlowLayout.LEFT)).apply { add(JLabel("Screen Elements")) }, BorderLayout.PAGE_START)
        leftPanel.add(JScrollPane(list), BorderLayout.CENTER)
        val buttonsPanel = JPanel(FlowLayout(FlowLayout.RIGHT)).apply {
            add(deleteButton)
            add(addButton)
        }
        leftPanel.add(buttonsPanel, BorderLayout.PAGE_END)

        val rightPanel = JPanel(BorderLayout())
        val namePanel = JPanel(GridLayout(1, 2))
        namePanel.add(JLabel("Name:"))
        namePanel.add(nameTextField)
        rightPanel.add(namePanel, BorderLayout.PAGE_START)

        add(leftPanel)
        add(rightPanel)
    }
}
