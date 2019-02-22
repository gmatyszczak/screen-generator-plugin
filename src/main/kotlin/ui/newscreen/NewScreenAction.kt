package ui.newscreen

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import data.file.CurrentPath

class NewScreenAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val currentPath = event.getData(DataKeys.VIRTUAL_FILE)?.let { CurrentPath(it.path, it.isDirectory) }
        NewScreenDialog(event.project!!, currentPath).show()
    }
}