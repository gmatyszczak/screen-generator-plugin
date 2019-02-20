package settings

import com.intellij.openapi.project.Project
import com.intellij.ui.CollectionListModel
import com.intellij.ui.LanguageTextField
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.util.ui.FormBuilder
import org.jetbrains.kotlin.idea.KotlinLanguage
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.ListSelectionModel

class SettingsJPanel : JPanel() {

    val nameTextField = JTextField()
    lateinit var fileEditorTextField: LanguageTextField
    lateinit var fileViewerEditorTextField: LanguageTextField

    val listModel = CollectionListModel<ScreenElement>()
    val list = JBList<ScreenElement>(listModel).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
    }
    val toolbarDecorator = ToolbarDecorator.createDecorator(list)

    init {
        layout = GridLayout(1, 2)
    }

    fun create(project: Project) {
        val toolbarPanel = toolbarDecorator.createPanel()
        fileEditorTextField = LanguageTextField(KotlinLanguage.INSTANCE, project, "", false)
        fileViewerEditorTextField = LanguageTextField(KotlinLanguage.INSTANCE, project, "", false)


        val rightPanel = JPanel(BorderLayout()).apply {
            val nameFormPanel = FormBuilder.createFormBuilder()
                    .addLabeledComponent("Name:", nameTextField)
                    .panel
            val filePanel = JPanel(GridLayout(2, 1)).apply {
                add(fileEditorTextField)
                add(fileViewerEditorTextField)
            }

            add(nameFormPanel, BorderLayout.PAGE_START)
            add(filePanel, BorderLayout.CENTER)
        }


        add(toolbarPanel)
        add(rightPanel)
    }
}
