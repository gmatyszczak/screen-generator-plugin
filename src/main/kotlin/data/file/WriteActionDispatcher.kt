package data.file

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import javax.inject.Inject

private const val COMMAND_NAME = "Screen Generator"
private const val GROUP_ID = "SCREEN_GENERATOR_ID"

interface WriteActionDispatcher {
    fun dispatch(action: () -> Unit)
}

class WriteActionDispatcherImpl @Inject constructor(
    val project: Project
) : WriteActionDispatcher {

    override fun dispatch(action: () -> Unit) =
        WriteCommandAction.runWriteCommandAction(project, COMMAND_NAME, GROUP_ID, {
            action()
        })
}
