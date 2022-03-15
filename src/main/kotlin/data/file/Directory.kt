package data.file

import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import model.Anchor.CLASS
import model.Anchor.FILE
import model.Anchor.FUNCTION
import model.Anchor.OBJECT
import model.Anchor.TAG
import model.FileType
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.KtPsiFactory

class Directory(
    private val project: Project,
    private val psiDirectory: PsiDirectory
) {

    fun findSubdirectory(name: String) = psiDirectory.findSubdirectory(name)?.let { Directory(project, it) }

    fun createSubdirectory(name: String) = Directory(project, psiDirectory.createSubdirectory(name))

    fun addFile(file: File) {
        val language = when (file.fileType) {
            FileType.KOTLIN -> KotlinLanguage.INSTANCE
            FileType.LAYOUT_XML -> XMLLanguage.INSTANCE
        }
        val psiFile = PsiFileFactory.getInstance(project)
            .createFileFromText("${file.name}.${file.fileType.extension}", language, file.content)
        psiDirectory.add(psiFile)
    }

    fun modifyFile(modification: Modification) {
        when (modification.fileType) {
            FileType.KOTLIN -> {
                val psiFile = psiDirectory.findFile("${modification.name}.${modification.fileType.extension}") as KtFile
                val (parent, anchor) = psiFile.findPsiParent(modification)
                KtPsiFactory(project)
                    .createFile(modification.content)
                    .children
                    .forEach { parent.addBefore(it, anchor) }
            }
            FileType.LAYOUT_XML -> Unit
        }
    }

    private fun KtFile.findPsiParent(modification: Modification): Pair<PsiElement, PsiElement?> =
        when (modification.anchor) {
            FILE -> this to null
            CLASS -> findChildrenByClass(KtClass::class.java)
                .first { it.name == modification.anchorName }
                .let { it.body!! to it.body!!.rBrace!! }
            OBJECT -> findChildrenByClass(KtObjectDeclaration::class.java)
                .first { it.name == modification.anchorName }
                .let { it.body!! to it.body!!.rBrace }
            FUNCTION -> findChildrenByClass(KtNamedFunction::class.java)
                .first { it.name == modification.anchorName }
                .let { it.bodyBlockExpression!! to it.bodyBlockExpression!!.rBrace }
            TAG -> throw IllegalStateException()
        }

    private fun PsiElement.findNestedChild() = Unit

}
