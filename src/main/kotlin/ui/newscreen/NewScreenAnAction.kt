package ui.newscreen

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.module.ModuleUtil
import data.file.CurrentPath
import model.Module

class NewScreenAnAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val currentPath = event.getData(PlatformDataKeys.VIRTUAL_FILE)?.let {
            val moduleName =
                ModuleUtil.findModuleForFile(it, event.project!!)?.name?.replaceAfterLastInclusive(".", "") ?: ""
            val module = Module(moduleName, moduleName.replace("${event.project!!.name}.", ""))
            CurrentPath(it.path, it.isDirectory, module)
        }
        NewScreenDialog(event.project!!, currentPath).show()
    }
}

private fun String.replaceAfterLastInclusive(delimiter: String, replacement: String): String {
    val index = lastIndexOf(delimiter)
    return if (index == -1) this else replaceRange(index, length, replacement)
}
