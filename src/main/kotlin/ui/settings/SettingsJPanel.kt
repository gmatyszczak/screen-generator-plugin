package ui.settings

import com.intellij.openapi.project.Project
import com.intellij.ui.CollectionListModel
import com.intellij.ui.JBSplitter
import com.intellij.ui.LanguageTextField
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.ui.layout.LCFlags
import com.intellij.ui.layout.panel
import model.ScreenElement
import org.jetbrains.kotlin.idea.KotlinLanguage
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.ListSelectionModel

class SettingsJPanel(project: Project) : JPanel() {

    val nameTextField = JTextField()
    val templateEditorTextField = LanguageTextField(KotlinLanguage.INSTANCE, project, "", false)
    val sampleEditorTextField = LanguageTextField(KotlinLanguage.INSTANCE, project, "", false).apply {
        isEnabled = false
    }

    val listModel = CollectionListModel<ScreenElement>()
    val list = JBList<ScreenElement>(listModel).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
    }
    val toolbarDecorator: ToolbarDecorator = ToolbarDecorator.createDecorator(list)

    val activityTextField = JTextField()
    val fragmentTextField = JTextField()

    init {
        layout = BorderLayout()
    }

    fun create() {
        val toolbarPanel = toolbarDecorator.createPanel()

        val androidComponentsPanel = panel(LCFlags.fillX, title = "Android Components") {
            row("Activity Base Class:") { activityTextField(growX) }
            row("Fragment Base Class:") { fragmentTextField(growX) }
        }

        val screenElementDetailsPanel = panel(LCFlags.fillX, title = "Screen Element Details") {
            row("Screen Element Name:") { nameTextField(growX) }
        }

        val templatePanel = panel(LCFlags.fillX, title = "Template") {
            row { templateEditorTextField(growX, growY, pushY) }
        }

        val samplePanel = panel(LCFlags.fillX, title = "Sample Code") {
            row { sampleEditorTextField(growX, growY, pushY) }
        }

        val rightPanel = panel(LCFlags.fillX) {
            row { androidComponentsPanel(growX) }
            row { screenElementDetailsPanel(growX) }
            row { templatePanel(growX, growY, pushY) }
            row { samplePanel(growX, growY, pushY) }
        }

        add(JBSplitter(0.3f).apply
        {
            firstComponent = toolbarPanel
            secondComponent = rightPanel
        }, BorderLayout.CENTER)
    }
}
