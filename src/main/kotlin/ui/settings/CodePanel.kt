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

    init {
        layout = GridLayout(2, 1)
    }

    fun create() {
        val templatePanel = panel(LCFlags.fillX, title = "Code Template") {
            row { kotlinTemplateTextField(growX, growY, pushY) }
            row { xmlTemplateTextField(growX, growY, pushY) }
        }
        val samplePanel = panel(LCFlags.fillX, title = "Sample Code") {
            row { kotlinSampleTextField(growX, growY, pushY) }
            row { xmlSampleTextField(growX, growY, pushY) }
        }
        add(templatePanel)
        add(samplePanel)
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
}