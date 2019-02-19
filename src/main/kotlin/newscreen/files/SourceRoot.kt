package newscreen.files

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager

interface SourceRoot {

    val path: String

    val directory: Directory
}

class SourceRootImpl(project: Project, virtualFile: VirtualFile) : SourceRoot {

    override val path = virtualFile.path

    override val directory = DirectoryImpl(project, PsiManager.getInstance(project).findDirectory(virtualFile)!!)
}
