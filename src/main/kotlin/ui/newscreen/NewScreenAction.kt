package ui.newscreen

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.module.ModuleUtil
import data.file.CurrentPath

class NewScreenAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val currentPath = event.getData(PlatformDataKeys.VIRTUAL_FILE)?.let {
            val module = ModuleUtil.findModuleForFile(it, event.project!!)?.name ?: ""
            CurrentPath(it.path, it.isDirectory, module)
        }
        NewScreenDialog(event.project!!, currentPath).show()
    }
}