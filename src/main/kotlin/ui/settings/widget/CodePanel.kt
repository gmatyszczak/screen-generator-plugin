package ui.settings.widget

import com.intellij.lang.Language
import com.intellij.openapi.project.Project
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.LanguageTextField
import com.intellij.ui.components.labels.LinkLabel
import model.FileType
import ui.settings.SettingsState
import util.addTextChangeListener
import util.updateText
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.BoxLayout.Y_AXIS
import javax.swing.JPanel

class CodePanel(
    private val project: Project,
    language: Language,
    private val fileType: FileType
) : JPanel() {

    var onTemplateTextChanged: ((String) -> Unit)? = null

    private val templateTextField = createLanguageTextField(language)
    private val sampleTextField = createLanguageTextField(language, isEnabled = false)

    private val templatePanel: JPanel
    private val samplePanel: JPanel

    var onHelpClicked: (() -> Unit)? = null

    private var listenersBlocked = false

    init {
        layout = GridLayout(2, 1)
        templatePanel = createTemplatePanel()
        samplePanel = createSamplePanel()
        add(templatePanel)
        add(samplePanel)
        templateTextField.addTextChangeListener { if (!listenersBlocked) onTemplateTextChanged?.invoke(it) }
    }

    private fun createTemplatePanel() =
        JPanel().apply {
            border = IdeBorderFactory.createTitledBorder("Code Template", false)
            layout = BoxLayout(this, Y_AXIS)
            add(templateTextField)
        }

    private fun createSamplePanel() =
        JPanel().apply {
            border = IdeBorderFactory.createTitledBorder("Sample Code", false)
            layout = BoxLayout(this, Y_AXIS)
            add(sampleTextField)
            add(JPanel(FlowLayout(FlowLayout.TRAILING)).apply {
                add(LinkLabel.create("Help") { onHelpClicked?.invoke() })
            })
        }

    private fun createLanguageTextField(language: Language, isEnabled: Boolean = true) =
        LanguageTextField(language, project, "", false).apply {
            this.isEnabled = isEnabled
        }

    fun render(state: SettingsState) {
        listenersBlocked = true
        val selectedElement = state.selectedElement
        if (selectedElement != null) {
            if (selectedElement.fileType == fileType) {
                isVisible = true
                templateTextField.updateText(selectedElement.template)
                sampleTextField.updateText(state.sampleCode)
            } else {
                isVisible = false
            }
        } else {
            isVisible = false
        }
        listenersBlocked = false
    }
}
