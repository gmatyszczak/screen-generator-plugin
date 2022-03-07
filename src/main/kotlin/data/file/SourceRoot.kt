package data.file

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager

class SourceRoot(project: Project, virtualFile: VirtualFile) {
    val path = virtualFile.path
    val directory: Directory = Directory(project, PsiManager.getInstance(project).findDirectory(virtualFile)!!)
}
