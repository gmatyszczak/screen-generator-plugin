package ui.settings

import com.intellij.lang.Language
import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.project.Project
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.LanguageTextField
import com.intellij.ui.components.labels.LinkLabel
import model.FileType
import org.jetbrains.kotlin.idea.KotlinLanguage
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.BoxLayout.Y_AXIS
import javax.swing.JPanel

class CodePanel(private val project: Project) : JPanel() {

    val kotlinTemplateTextField = createLanguageTextField(KotlinLanguage.INSTANCE)
    val kotlinSampleTextField = createLanguageTextField(KotlinLanguage.INSTANCE, isEnabled = false)
    val xmlTemplateTextField = createLanguageTextField(XMLLanguage.INSTANCE, isVisible = false)
    val xmlSampleTextField = createLanguageTextField(XMLLanguage.INSTANCE, isVisible = false, isEnabled = false)

    private val textFieldsMap = mapOf(
            FileType.KOTLIN to listOf(kotlinTemplateTextField, kotlinSampleTextField),
            FileType.LAYOUT_XML to listOf(xmlTemplateTextField, xmlSampleTextField)
    )
    private var currentFileType = FileType.KOTLIN
    private lateinit var templatePanel: JPanel
    private lateinit var samplePanel: JPanel

    init {
        layout = GridLayout(2, 1)
    }

    fun create(onHelpClick: () -> Unit) {
        templatePanel = createTemplatePanel()
        samplePanel = createSamplePanel(onHelpClick)
        add(templatePanel)
        add(samplePanel)
    }

    private fun createSamplePanel(onHelpClick: () -> Unit) =
            JPanel().apply {
                border = IdeBorderFactory.createTitledBorder("Sample Code", false)
                layout = BoxLayout(this, Y_AXIS)
                add(kotlinSampleTextField)
                add(xmlSampleTextField)
                add(JPanel(FlowLayout(FlowLayout.TRAILING)).apply { add(LinkLabel.create("Help", onHelpClick)) })
            }

    private fun createTemplatePanel() =
            JPanel().apply {
                border = IdeBorderFactory.createTitledBorder("Code Template", false)
                layout = BoxLayout(this, Y_AXIS)
                add(kotlinTemplateTextField)
                add(xmlTemplateTextField)
            }

    fun show(fileType: FileType) {
        textFieldsMap[currentFileType]?.forEach { it.isVisible = false }
        currentFileType = fileType
        textFieldsMap[currentFileType]?.forEach { it.isVisible = true }
    }

    private fun createLanguageTextField(language: Language, isVisible: Boolean = true, isEnabled: Boolean = true) =
            LanguageTextField(language, project, "", false).apply {
                this.isVisible = isVisible
                this.isEnabled = isEnabled
            }

    fun setCodePanelsEnabled(isEnabled: Boolean) {
        templatePanel.isEnabled = isEnabled
        samplePanel.isEnabled = isEnabled
        textFieldsMap[currentFileType]?.get(0)?.isEnabled = isEnabled
    }
}