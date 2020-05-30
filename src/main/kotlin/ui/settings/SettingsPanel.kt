package ui.settings

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.CollectionListModel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.JBSplitter
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import model.Category
import model.FileType
import model.ScreenElement
import java.awt.*
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.ListSelectionModel
import javax.swing.SwingConstants.RIGHT

class SettingsPanel(project: Project) : JPanel() {

    val categoryNameTextField = JTextField()
    val screenElementNameTextField = JTextField()

    val categoriesListModel = CollectionListModel<Category>()
    val categoriesList = JBList<Category>(categoriesListModel).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
    }
    val categoryToolbarDecorator: ToolbarDecorator = ToolbarDecorator.createDecorator(categoriesList)

    val screenElementsListModel = CollectionListModel<ScreenElement>()
    val screenElementsList = JBList<ScreenElement>(screenElementsListModel).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
    }
    val screenElementToolbarDecorator: ToolbarDecorator = ToolbarDecorator.createDecorator(screenElementsList)

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
        add(createVerticalSplitter(createCategoriesPanel(), createSplitter(screenElementsPanel, rightPanel)), BorderLayout.PAGE_START)
        addCodePanel(onHelpClick)
    }

    private fun createCategoriesPanel() = JPanel().apply {
        border = IdeBorderFactory.createTitledBorder("Categories", false)
        layout = GridLayout(0, 1)
        val categoryLabel = JLabel("Category Name: ")
        categoryLabel.horizontalAlignment = RIGHT
        val categoryNameSplitter = JBSplitter(0.5f).apply {
            firstComponent = categoryLabel
            secondComponent = categoryNameTextField
        }
        val categorySplitter = JBSplitter(0.3f).apply {
            firstComponent = categoryToolbarDecorator.createPanel()
            secondComponent = categoryNameSplitter
        }
        add(categorySplitter)
    }

    private fun createScreenElementsPanel() = JPanel().apply {
        border = IdeBorderFactory.createTitledBorder("Screen Elements", false)
        layout = GridLayout(1, 1)
        add(screenElementToolbarDecorator.createPanel())
    }

    private fun createAndroidComponentsPanel() = JPanel().apply {
        border = IdeBorderFactory.createTitledBorder("Android Components", false)
        layout = GridBagLayout()
        add(JLabel("Activity Base Class:"), constraintsLeft(0, 0))
        add(activityTextField, constraintsRight(1, 0))
        add(JLabel("Fragment Base Class:"), constraintsLeft(0, 1))
        add(fragmentTextField, constraintsRight(1, 1))
    }

    private fun createScreenElementDetailsPanel() = JPanel().apply {
        border = IdeBorderFactory.createTitledBorder("Screen Element Details", false)
        layout = GridBagLayout()
        add(screenElementNameLabel, constraintsLeft(0, 0))
        add(screenElementNameTextField, constraintsRight(1, 0))
        add(fileNameLabel, constraintsLeft(0, 1))
        add(fileNameTextField, constraintsRight(1, 1))
        add(fileTypeLabel, constraintsLeft(0, 2))
        add(fileTypeComboBox, constraintsRight(1, 2).apply {
            gridwidth = 1
            fill = GridBagConstraints.NONE
            anchor = GridBagConstraints.LINE_START
        })
        add(fileNameSampleLabel, constraintsRight(2, 2).apply {
            gridwidth = 1
            weightx /= 2
            anchor = GridBagConstraints.LINE_END
            fill = GridBagConstraints.NONE
        })
    }

    private fun constraintsLeft(x: Int, y: Int) = GridBagConstraints().apply {
        weightx = 0.15
        gridx = x
        gridy = y
        insets = Insets(0, 8, 0, 0)
        fill = GridBagConstraints.HORIZONTAL
    }

    private fun constraintsRight(x: Int, y: Int) = GridBagConstraints().apply {
        weightx = 0.85
        gridx = x
        gridy = y
        gridwidth = 2
        fill = GridBagConstraints.HORIZONTAL
    }

    private fun createVerticalSplitter(leftPanel: JPanel, rightPanel: JPanel): JPanel =
            JBSplitter(true, 0.4f).apply {
                firstComponent = leftPanel
                secondComponent = rightPanel
            }

    private fun createSplitter(leftPanel: JPanel, rightPanel: JPanel): JPanel =
            JBSplitter(0.2f).apply {
                firstComponent = leftPanel
                secondComponent = rightPanel
            }

    private fun createSplitterRightPanel(androidComponentsPanel: JPanel, screenElementDetailsPanel: JPanel) =
            JPanel(GridLayout(0, 1)).apply {
                add(androidComponentsPanel)
                add(screenElementDetailsPanel)
            }

    private fun addCodePanel(onHelpClick: () -> Unit) {
        codePanel.create(onHelpClick)
        add(codePanel, BorderLayout.CENTER)
    }

    fun setScreenElementDetailsEnabled(isEnabled: Boolean) {
        screenElementDetailsPanel.isEnabled = isEnabled
        screenElementNameTextField.isEnabled = isEnabled
        fileNameTextField.isEnabled = isEnabled
        fileTypeComboBox.isEnabled = isEnabled
        screenElementNameLabel.isEnabled = isEnabled
        fileNameLabel.isEnabled = isEnabled
        fileTypeLabel.isEnabled = isEnabled
        codePanel.setCodePanelsEnabled(isEnabled)
    }
}
