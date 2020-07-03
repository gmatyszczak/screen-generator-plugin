package data.file

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project

private const val COMMAND_NAME = "Screen Generator"
private const val GROUP_ID = "SCREEN_GENERATOR_ID"

interface WriteActionDispatcher {
    fun dispatch(action: () -> Unit)
}

class WriteActionDispatcherImpl(val project: Project) : WriteActionDispatcher {

    override fun dispatch(action: () -> Unit) = WriteCommandAction.runWriteCommandAction(project, COMMAND_NAME, GROUP_ID, {
        action()
    }, arrayOf())
}
