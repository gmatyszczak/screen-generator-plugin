package ui.settings.widget

import com.intellij.lang.Language
import com.intellij.openapi.project.Project
import com.intellij.ui.EditorTextField
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.LanguageTextField
import com.intellij.ui.components.JBScrollPane
import model.FileType
import ui.settings.SettingsState
import util.addTextChangeListener
import util.updateText
import java.awt.Dimension
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
    private val templateTextField = createTemplateTextField(language)
    private val sampleTextField = createSampleTextField()
    private var listenersBlocked = false

    init {
        layout = GridLayout(2, 1)
        val templatePanel = createTemplatePanel()
        val samplePanel = createSamplePanel()
        add(templatePanel)
        add(samplePanel)
        templateTextField.addTextChangeListener { if (!listenersBlocked) onTemplateTextChanged?.invoke(it) }
    }

    private fun createTemplatePanel() =
        JPanel().apply {
            border = IdeBorderFactory.createTitledBorder("Code Template", false)
            layout = BoxLayout(this, Y_AXIS)
            add(JBScrollPane(templateTextField))
        }

    private fun createSamplePanel() =
        JPanel().apply {
            border = IdeBorderFactory.createTitledBorder("Sample Code", false)
            layout = BoxLayout(this, Y_AXIS)
            add(JBScrollPane(sampleTextField))
        }

    private fun createTemplateTextField(language: Language) =
        LanguageTextField(language, project, "", false)

    private fun createSampleTextField() =
        EditorTextField().apply { isEnabled = false }

    fun render(state: SettingsState) {
        listenersBlocked = true
        val selectedElement = state.selectedElement
        if (selectedElement != null) {
            setEnabledAll(true)
            if (selectedElement.fileType == fileType) {
                isVisible = true
                templateTextField.updateText(selectedElement.template)
                sampleTextField.updateText(state.sampleCode)
            } else {
                isVisible = false
            }
        } else {
            isVisible = false
            setEnabledAll(false)
        }
        listenersBlocked = false
    }

    private fun setEnabledAll(isEnabled: Boolean) {
        templateTextField.isEnabled = isEnabled
    }

    override fun getPreferredSize(): Dimension? {
        val original = super.getPreferredSize()
        return Dimension(original.width, 600)
    }
}
