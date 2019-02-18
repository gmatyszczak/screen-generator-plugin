package newscreen

import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager

class NewScreenDialog(private val project: Project) : DialogWrapper(true) {

    private val panel = NewScreenJPanel()

    init {
        init()
    }

    override fun doOKAction() {
        val directory = PsiManager.getInstance(project).findDirectory(project.baseDir)
        val file = PsiFileFactory.getInstance(project).createFileFromText("${panel.nameTextField.text}.kt", JavaLanguage.INSTANCE, "Text")
        directory!!.add(file)
        close(DialogWrapper.OK_EXIT_CODE)
    }

    override fun createCenterPanel() = panel
}