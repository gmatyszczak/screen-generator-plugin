package newscreen.files

import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory

interface Directory {

    fun findSubdirectory(name: String): Directory?
    fun createSubdirectory(name: String): Directory
    fun addFile(file: File)
}

class DirectoryImpl(private val project: Project,
                    private val psiDirectory: PsiDirectory) : Directory {

    override fun findSubdirectory(name: String) = psiDirectory.findSubdirectory(name)?.let { DirectoryImpl(project, it) }

    override fun createSubdirectory(name: String) = DirectoryImpl(project, psiDirectory.createSubdirectory(name))

    override fun addFile(file: File) = ApplicationManager.getApplication().runWriteAction {
        val psiFile = PsiFileFactory.getInstance(project).createFileFromText("${file.name}.kt", JavaLanguage.INSTANCE, file.content)
        psiDirectory.add(psiFile)
    }
}
