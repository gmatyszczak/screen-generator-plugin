package newscreen

import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager

interface FileCreator {

    fun createScreenFiles(screenName: String)
}

class FileCreatorImpl(private val project: Project) : FileCreator {

    override fun createScreenFiles(screenName: String) {
        ApplicationManager.getApplication().runWriteAction {
            val directory = PsiManager.getInstance(project).findDirectory(project.baseDir)
            val file = PsiFileFactory.getInstance(project).createFileFromText("$screenName.kt", JavaLanguage.INSTANCE, "Text")
            directory!!.add(file)
        }
    }
}