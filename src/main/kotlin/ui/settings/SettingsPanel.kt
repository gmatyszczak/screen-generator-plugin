package ui.settings

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.CollectionListModel
import com.intellij.ui.JBSplitter
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.ui.layout.LCFlags
import com.intellij.ui.layout.panel
import model.FileType
import model.ScreenElement
import java.awt.BorderLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.ListSelectionModel

class SettingsPanel(project: Project) : JPanel() {

    val nameTextField = JTextField()

    val screenElementsListModel = CollectionListModel<ScreenElement>()
    val screenElementsList = JBList<ScreenElement>(screenElementsListModel).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
    }
    val toolbarDecorator: ToolbarDecorator = ToolbarDecorator.createDecorator(screenElementsList)

    val activityTextField = JTextField()
    val fragmentTextField = JTextField()

    val fileTypeComboBox = ComboBox<FileType>(FileType.values())
    val fileNameTextField = JTextField()
    val fileNameSampleLabel = JLabel()

    val codePanel = CodePanel(project)

    init {
        layout = BorderLayout()
    }

    fun create(onHelpClick: () -> Unit) {
        val screenElementsPanel = createScreenElementsPanel()
        val androidComponentsPanel = createAndroidComponentsPanel(onHelpClick)
        val screenElementDetailsPanel = createScreenElementDetailsPanel()

        val rightPanel = createSplitterRightPanel(androidComponentsPanel, screenElementDetailsPanel)
        val leftPanel = createSplitterLeftPanel(screenElementsPanel)

        addSplitter(leftPanel, rightPanel)
        addCodePanel()
    }

    private fun createScreenElementsPanel(): JPanel {
        return panel(LCFlags.fillX, title = "Screen Elements") {
            row { toolbarDecorator.createPanel()(growX, growY, pushY) }
        }
    }

    private fun createAndroidComponentsPanel(onHelpClick: () -> Unit): JPanel {
        return panel(LCFlags.fillX, title = "Android Components") {
            row("Activity Base Class:") { activityTextField() }
            row("Fragment Base Class:") { fragmentTextField() }
            row {
                link("Help", action = onHelpClick)
            }
        }
    }

    private fun createScreenElementDetailsPanel(): JPanel {
        return panel(LCFlags.fillX, title = "Screen Element Details") {
            row("Screen Element Name:") { nameTextField() }
            row("File Name:") {
                fileNameTextField()
                right { fileNameSampleLabel() }
            }
            row("File Type:") { fileTypeComboBox() }
        }
    }

    private fun addSplitter(leftPanel: JPanel, rightPanel: JPanel) {
        add(JBSplitter(0.3f).apply {
            firstComponent = leftPanel
            secondComponent = rightPanel
        }, BorderLayout.PAGE_START)
    }

    private fun createSplitterRightPanel(androidComponentsPanel: JPanel, screenElementDetailsPanel: JPanel): JPanel {
        return panel(LCFlags.fillX) {
            row { androidComponentsPanel(growX) }
            row { screenElementDetailsPanel(growX) }
        }
    }

    private fun createSplitterLeftPanel(screenElementsPanel: JPanel): JPanel {
        return panel(LCFlags.fillX) {
            row { screenElementsPanel(growX, growY, pushY) }
        }
    }

    private fun addCodePanel() {
        codePanel.create()
        add(codePanel, BorderLayout.CENTER)
    }
}
