package settings

import com.intellij.openapi.project.Project
import com.intellij.ui.CollectionListModel
import com.intellij.ui.LanguageTextField
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.util.ui.FormBuilder
import model.ScreenElement
import org.jetbrains.kotlin.idea.KotlinLanguage
import java.awt.BorderLayout
import java.awt.GridLayout
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

    init {
        layout = GridLayout(1, 2)
    }

    fun create() {
        val toolbarPanel = toolbarDecorator.createPanel()

        val rightPanel = JPanel(BorderLayout()).apply {
            val nameFormPanel = FormBuilder.createFormBuilder()
                    .addLabeledComponent("Screen Element:", nameTextField)
                    .panel
            val filePanel = JPanel(GridLayout(2, 1)).apply {
                add(templateEditorTextField)
                add(sampleEditorTextField)
            }

            add(nameFormPanel, BorderLayout.PAGE_START)
            add(filePanel, BorderLayout.CENTER)
        }


        add(toolbarPanel)
        add(rightPanel)
    }
}
