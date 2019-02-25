package ui.settings

import com.intellij.lang.Language
import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.project.Project
import com.intellij.ui.LanguageTextField
import com.intellij.ui.layout.LCFlags
import com.intellij.ui.layout.panel
import model.FileType
import org.jetbrains.kotlin.idea.KotlinLanguage
import java.awt.GridLayout
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
            panel(LCFlags.fillX, title = "Sample Code") {
                row { kotlinSampleTextField(growX, growY, pushY) }
                row { xmlSampleTextField(growX, growY, pushY) }
                row {
                    right { link("Help", action = onHelpClick) }
                }
            }

    private fun createTemplatePanel() =
            panel(LCFlags.fillX, title = "Code Template") {
                row { kotlinTemplateTextField(growX, growY, pushY) }
                row { xmlTemplateTextField(growX, growY, pushY) }
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
        textFieldsMap[currentFileType]?.forEach { it.isEnabled = isEnabled }
    }
}