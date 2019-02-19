package newscreen

import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import settings.ScreenGeneratorComponent

interface FileCreator {

    fun createScreenFiles(screenName: String)
}

class FileCreatorImpl(private val project: Project) : FileCreator {

    override fun createScreenFiles(screenName: String) {
        ApplicationManager.getApplication().runWriteAction {
            val sourceRoots = ProjectRootManager.getInstance(project).contentSourceRoots.filter {
                !it.path.contains("build", true)
                        && !it.path.contains("test", true)
                        && !it.path.contains("res")
            }
            val screenElements = ScreenGeneratorComponent.getInstance(project).settings.screenElements
            val directory = PsiManager.getInstance(project).findDirectory(sourceRoots[0])
            screenElements.forEach {
                val file = PsiFileFactory.getInstance(project).createFileFromText("$screenName${it.name}.kt", JavaLanguage.INSTANCE, "Text")
                directory!!.add(file)
            }
        }
    }
}