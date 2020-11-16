package ui.settings.widget

import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.project.Project
import com.intellij.ui.JBSplitter
import model.FileType
import org.jetbrains.kotlin.idea.KotlinLanguage
import ui.settings.SettingsState
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JPanel

class SettingsPanel(project: Project) : JPanel() {

    val screenElementsPanel = ScreenElementsPanel()
    val categoriesPanel = CategoriesPanel()
    val screenElementDetailsPanel = ScreenElementDetailsPanel()
    val codePanels: Map<FileType, CodePanel>

    var onTemplateTextChanged: ((String) -> Unit)? = null
        set(value) {
            field = value
            codePanels.forEach { _, panel -> panel.onTemplateTextChanged = value }
        }

    var onHelpClicked: (() -> Unit)? = null
        set(value) {
            field = value
            codePanels.forEach { _, panel -> panel.onHelpClicked = value }
        }

    init {
        layout = BorderLayout()
        val leftPanel = JBSplitter(0.5f).apply {
            firstComponent = categoriesPanel
            secondComponent = screenElementsPanel
        }
        addSplitter(leftPanel, screenElementDetailsPanel)
        codePanels = mapOf(
            FileType.KOTLIN to CodePanel(project, KotlinLanguage.INSTANCE, FileType.KOTLIN),
            FileType.LAYOUT_XML to CodePanel(project, XMLLanguage.INSTANCE, FileType.LAYOUT_XML)
        )
        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            codePanels.forEach { (_, panel) -> add(panel) }
        }, BorderLayout.CENTER)
    }

    private fun addSplitter(leftPanel: JPanel, rightPanel: JPanel) {
        add(JBSplitter(0.4f).apply {
            firstComponent = leftPanel
            secondComponent = rightPanel
        }, BorderLayout.PAGE_START)
    }

    fun render(state: SettingsState) {
        categoriesPanel.render(state)
        screenElementsPanel.render(state)
        screenElementDetailsPanel.render(state)
        codePanels.values.forEach { it.render(state) }
    }
}
