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
    val fileNameSampleLabel = JLabel().apply { isEnabled = false }

    val codePanel = CodePanel(project)

    private lateinit var screenElementDetailsPanel: JPanel
    private val screenElementNameLabel = JLabel("Screen Element Name:")
    private val fileNameLabel = JLabel("File Name:")
    private val fileTypeLabel = JLabel("File Type:")

    init {
        layout = BorderLayout()
    }

    fun create(onHelpClick: () -> Unit) {
        val screenElementsPanel = createScreenElementsPanel()
        val androidComponentsPanel = createAndroidComponentsPanel()
        screenElementDetailsPanel = createScreenElementDetailsPanel()

        val rightPanel = createSplitterRightPanel(androidComponentsPanel, screenElementDetailsPanel)
        val leftPanel = createSplitterLeftPanel(screenElementsPanel)

        addSplitter(leftPanel, rightPanel)
        addCodePanel(onHelpClick)
    }

    private fun createScreenElementsPanel() =
            panel(LCFlags.fillX, title = "Screen Elements") {
                row { toolbarDecorator.createPanel()(growX, growY, pushY) }
            }

    private fun createAndroidComponentsPanel() =
            panel(LCFlags.fillX, title = "Android Components") {
                row("Activity Base Class:") { activityTextField() }
                row("Fragment Base Class:") { fragmentTextField() }
            }

    private fun createScreenElementDetailsPanel() =
            panel(LCFlags.fillX, title = "Screen Element Details") {
                row(screenElementNameLabel) {
                    nameTextField() }
                row(fileNameLabel) {
                    fileNameTextField()
                    right { fileNameSampleLabel() }
                }
                row(fileTypeLabel) {
                    fileTypeComboBox()
                }
            }

    private fun addSplitter(leftPanel: JPanel, rightPanel: JPanel) {
        add(JBSplitter(0.2f).apply {
            firstComponent = leftPanel
            secondComponent = rightPanel
        }, BorderLayout.PAGE_START)
    }

    private fun createSplitterRightPanel(androidComponentsPanel: JPanel, screenElementDetailsPanel: JPanel) =
            panel(LCFlags.fillX) {
                row { androidComponentsPanel(growX) }
                row { screenElementDetailsPanel(growX) }
            }

    private fun createSplitterLeftPanel(screenElementsPanel: JPanel) =
            panel(LCFlags.fillX) {
                row { screenElementsPanel(growX, growY, pushY) }
            }

    private fun addCodePanel(onHelpClick: () -> Unit) {
        codePanel.create(onHelpClick)
        add(codePanel, BorderLayout.CENTER)
    }

    fun setScreenElementDetailsEnabled(isEnabled: Boolean) {
        screenElementDetailsPanel.isEnabled = isEnabled
        nameTextField.isEnabled = isEnabled
        fileNameTextField.isEnabled = isEnabled
        fileTypeComboBox.isEnabled = isEnabled
        screenElementNameLabel.isEnabled = isEnabled
        fileNameLabel.isEnabled = isEnabled
        fileTypeLabel.isEnabled = isEnabled
        codePanel.setCodePanelsEnabled(isEnabled)
    }
}
