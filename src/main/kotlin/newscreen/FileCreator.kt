package newscreen

import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import settings.ScreenGeneratorComponent

interface FileCreator {

    fun createScreenFiles(screenName: String)
}

class FileCreatorImpl(private val project: Project) : FileCreator {

    override fun createScreenFiles(screenName: String) {
        ApplicationManager.getApplication().runWriteAction {
            val screenElements = ScreenGeneratorComponent.getInstance(project).settings.screenElements
            val directory = PsiManager.getInstance(project).findDirectory(project.baseDir)
            screenElements.forEach {
                val file = PsiFileFactory.getInstance(project).createFileFromText("$screenName${it.name}.kt", JavaLanguage.INSTANCE, "Text")
                directory!!.add(file)
            }
        }
    }
}